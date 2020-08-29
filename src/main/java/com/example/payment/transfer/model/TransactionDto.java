package com.example.payment.transfer.model;

import com.example.payment.transfer.enums.TransactionCategory;

import javax.persistence.spi.PersistenceUnitTransactionType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransactionDto {

    @NotNull(message = "from Wallet cannot be null")
    private Long fromWalletId;

    @NotNull(message = "to Wallet cannot be null")
    private Long toWalletId;
    @NotNull(message = "amount cannot be null")
    private BigDecimal amount;
    @NotNull(message = "transaction category cannot be null")
    private TransactionCategory transactionCategory;

    public Long getFromWalletId() {
        return fromWalletId;
    }

    public void setFromWalletId(Long fromWalletId) {
        this.fromWalletId = fromWalletId;
    }

    public Long getToWalletId() {
        return toWalletId;
    }

    public void setToWalletId(Long toWalletId) {
        this.toWalletId = toWalletId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransactionCategory getTransactionCategory() {
        return transactionCategory;
    }

    public void setTransactionCategory(TransactionCategory transactionCategory) {
        this.transactionCategory = transactionCategory;
    }

    public Boolean isTransactionCategorySelfDeposit(){
        return TransactionCategory.SELF_DEPOSIT.equals(this.transactionCategory);
    }
}
