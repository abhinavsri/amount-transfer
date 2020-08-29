package com.example.payment.transfer.controller;

import com.example.payment.transfer.model.PassbookDTO;
import com.example.payment.transfer.service.TransactionService;
import com.example.payment.transfer.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class TransactionController {

    @Autowired
    private TransactionService transactionService;

    @Autowired
    private WalletService walletService;

    @GetMapping("/passbook/{userId}/wallet/{walletId}")
    public List<PassbookDTO> getPassbook(@PathVariable("userId") Long userId,
                                                         @PathVariable("walletId") Long walletId){
        return transactionService.getPassbook(userId,walletId);

    }

}
