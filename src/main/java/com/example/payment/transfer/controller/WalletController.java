package com.example.payment.transfer.controller;

import com.example.payment.transfer.exception.UserNotFoundException;
import com.example.payment.transfer.model.Transaction;
import com.example.payment.transfer.model.TransactionDto;
import com.example.payment.transfer.model.User;
import com.example.payment.transfer.model.Wallet;
import com.example.payment.transfer.service.TransactionService;
import com.example.payment.transfer.service.UserService;
import com.example.payment.transfer.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class WalletController {


    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;

    @GetMapping("user/{userId}/wallets/{walletId}")
    public ResponseEntity<Wallet> getWallet(@PathVariable("userId") Long userId,
                                            @PathVariable("walletId") Long walletId){


        return ResponseEntity.ok().body(walletService.getWallet(userId,walletId));
    }

    @PostMapping("user/{userId}/wallets")
    public ResponseEntity<Wallet> createWallet(@PathVariable("userId") Long userId,
                                            @Valid @RequestBody Wallet wallet){


        return ResponseEntity.ok().body(walletService.createWallet(wallet,userId));
    }
    @PostMapping("user/{userId}/selfDeposit")
    public ResponseEntity<Transaction> selfDeposit(@PathVariable("userId") Long userId,
                                            @RequestBody TransactionDto transactionDto){

        Transaction transaction =transactionService.selfDeposit(transactionDto);
        return ResponseEntity.ok().body(transaction);
    }

    @PostMapping("user/{userId}/transferAmount")
    public ResponseEntity<Transaction> transferAmount(@PathVariable("userId") Long userId,
                                              @RequestBody TransactionDto transactionDto){

        Transaction transaction =transactionService.transferAmount(transactionDto);
        return ResponseEntity.ok().body(transaction);
    }


}
