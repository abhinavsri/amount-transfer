package com.example.payment.transfer.service;

import com.example.payment.transfer.enums.TransactionCategory;
import com.example.payment.transfer.exception.AmountInWalletExceededException;
import com.example.payment.transfer.exception.SelfDepositException;
import com.example.payment.transfer.exception.TransferException;
import com.example.payment.transfer.exception.WalletNotFoundException;
import com.example.payment.transfer.model.*;
import com.example.payment.transfer.repository.TransactionRepository;
import com.example.payment.transfer.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class TransactionService {
    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private WalletService walletService;

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public Transaction selfDeposit(TransactionDto transactionDto) {

        if (TransactionCategory.SELF_DEPOSIT.equals(transactionDto.getTransactionCategory())) {
            if (!transactionDto.getFromWalletId().equals(transactionDto.getToWalletId())) {
                throw new SelfDepositException("From wallet Id must be equal to To wallet id in case of self Deposit");
            }
        }
        Wallet toWallet = null;
        Wallet fromWallet = walletRepository.getWallet(transactionDto.getFromWalletId())
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for this id " + transactionDto.getFromWalletId()));
        toWallet = fromWallet;
        if (transactionDto.getAmount().compareTo(fromWallet.getAvailableBalance()) < 0 && !transactionDto.isTransactionCategorySelfDeposit()) {
            throw new AmountInWalletExceededException("The amount is more than available balance");

        }
        Transaction transaction = new Transaction();
        transaction.setToWallet(toWallet);
        transaction.setToBalance(toWallet.getAvailableBalance());
        transaction.setFromWallet(fromWallet);
        transaction.setCategory(TransactionCategory.SELF_DEPOSIT);
        transaction.setFromBalance(fromWallet.getAvailableBalance());
        transaction.setAmount(transactionDto.getAmount());
        if (TransactionCategory.SELF_DEPOSIT.equals(transactionDto.getTransactionCategory())) {
            toWallet.deposit(transactionDto.getAmount());
            walletRepository.save(toWallet);
        }
        return transactionRepository.save(transaction);
    }


    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public Transaction transferAmount(TransactionDto transactionDto) {

        if (TransactionCategory.TRANSFER.equals(transactionDto.getTransactionCategory())) {
            if (transactionDto.getFromWalletId().equals(transactionDto.getToWalletId())) {
                throw new TransferException("In case of Transfer , the from Wallet cannot be equal to to Wallet");
            }
        }
        Wallet fromWallet = walletRepository.getWallet(transactionDto.getFromWalletId())
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for this id " + transactionDto.getFromWalletId()));
        Wallet toWallet = walletRepository.getWallet(transactionDto.getToWalletId())
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for this id " + transactionDto.getToWalletId()));
        if (transactionDto.getAmount().compareTo(fromWallet.getAvailableBalance()) < 0 && !transactionDto.isTransactionCategorySelfDeposit()) {
            throw new AmountInWalletExceededException("The amount is more than available balance");

        }
        Transaction transaction = new Transaction();
        transaction.setToWallet(toWallet);
        transaction.setCategory(TransactionCategory.TRANSFER);
        transaction.setToBalance(toWallet.getAvailableBalance());
        transaction.setFromWallet(fromWallet);
        transaction.setFromBalance(fromWallet.getAvailableBalance());
        transaction.setAmount(transactionDto.getAmount());
        fromWallet.deposit(transactionDto.getAmount());
        walletRepository.save(fromWallet);


        toWallet.deposit(transactionDto.getAmount());
        walletRepository.save(toWallet);

        return transactionRepository.save(transaction);
    }

    public List<PassbookDTO> getPassbook(Long userId,Long walletId){
        Wallet wallet = walletService.getWallet(userId,walletId);
        User user = userService.getUser(userId);
        List<Transaction> transactions=transactionRepository.findAllByFromWalletOrToWallet(wallet);
        List<PassbookDTO> passbookDTOList = new ArrayList<>();
        passbookDTOList = transactions.stream().
                map((Transaction transaction)->new PassbookDTO(transaction, wallet))
                .collect(Collectors.toList());
        return  passbookDTOList;
    }
}
