package com.example.payment.transfer.service;

import com.example.payment.transfer.enums.TransactionCategory;
import com.example.payment.transfer.exception.AmountInWalletExceededException;
import com.example.payment.transfer.exception.SelfDepositException;
import com.example.payment.transfer.exception.UserNotFoundException;
import com.example.payment.transfer.exception.WalletNotFoundException;
import com.example.payment.transfer.model.Transaction;
import com.example.payment.transfer.model.TransactionDto;
import com.example.payment.transfer.model.User;
import com.example.payment.transfer.model.Wallet;
import com.example.payment.transfer.repository.TransactionRepository;
import com.example.payment.transfer.repository.WalletRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;


@Service
public class WalletService {


    @Autowired
    private WalletRepository walletRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private UserService userService;


    public Wallet getWallet(Long userId, Long walletId) throws UserNotFoundException {
        User user = userService.getUser(userId);
        Wallet wallet = walletRepository.findByUserIdAndId(userId,walletId)
                .orElseThrow(()->new WalletNotFoundException("Wallet not found for this id "+walletId +" and user is "+userId));
        return wallet;
    }
    public Wallet createWallet(Wallet wallet, Long userId) throws UserNotFoundException {
        User existingUser = userService.getUser(userId);
        userService.findById(userId).map(user ->{
            wallet.setUser(existingUser);
            return user;
        }).orElseThrow(() -> new UserNotFoundException("User with id " + userId + " not found"));
        return walletRepository.save(wallet);
    }
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.SERIALIZABLE, rollbackFor = Exception.class)
    public Transaction selfDeposit(TransactionDto transactionDto) {

       if(TransactionCategory.SELF_DEPOSIT.equals(transactionDto.getTransactionCategory())) {
           if(!transactionDto.getFromWalletId().equals(transactionDto.getToWalletId())){
               throw new SelfDepositException("From wallet Id must be equal to To wallet id in case of self Deposit");
           }
       }
        Wallet toWallet =null;
        Wallet fromWallet = walletRepository.getWallet(transactionDto.getFromWalletId())
               .orElseThrow(()->new WalletNotFoundException("Wallet not found for this id "+transactionDto.getFromWalletId()));
        if(transactionDto.isTransactionCategorySelfDeposit()){
            toWallet= fromWallet;
        }else{
            toWallet = walletRepository.getWallet(transactionDto.getToWalletId())
                    .orElseThrow(()->new WalletNotFoundException("Wallet not found for this id "+transactionDto.getToWalletId()));
        }
        if(transactionDto.getAmount().compareTo(fromWallet.getAvailableBalance()) <0 && !transactionDto.isTransactionCategorySelfDeposit()){
            throw new AmountInWalletExceededException("The amount is more than available balance");

        }
        Transaction transaction = new Transaction();
        transaction.setToWallet(toWallet);
        transaction.setToBalance(toWallet.getAvailableBalance());
        transaction.setFromWallet(fromWallet);
        transaction.setFromBalance(fromWallet.getAvailableBalance());
        transaction.setAmount(transactionDto.getAmount());
        if(TransactionCategory.SELF_DEPOSIT.equals(transactionDto.getTransactionCategory())) {

            toWallet.deposit(transactionDto.getAmount());
            walletRepository.save(toWallet);
        }
        return transactionRepository.save(transaction);
    }



}
