package com.example.payment.transfer.model;

import com.example.payment.transfer.enums.TransactionCategory;

public class PassbookDTO {
    private String previousBalance;
    private String currentBalance;
    private String amount;
    private String transactionCategory;


    public PassbookDTO(Transaction transaction, Wallet wallet) {

        if(wallet.equals(transaction.getFromWallet())){
            this.previousBalance = transaction.getFromBalance().toString();
            this.currentBalance = transaction.getFromBalance().add(transaction.getAmount()).toString();
            this.transactionCategory = transaction.getCategory().toString();
        }else {
            this.previousBalance = transaction.getFromBalance().toString();
            this.currentBalance = transaction.getFromBalance().subtract(transaction.getAmount()).toString();
            this.transactionCategory = transaction.getCategory().toString();
        }
        this.amount = transaction.getAmount().toString();
    }
}
