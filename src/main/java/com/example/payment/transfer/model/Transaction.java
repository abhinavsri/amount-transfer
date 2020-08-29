package com.example.payment.transfer.model;

import com.example.payment.transfer.enums.TransactionCategory;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name="transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // to set as autoincrement
    private Long id;
    private BigDecimal amount = BigDecimal.ZERO;

    @ManyToOne
    private Wallet fromWallet;

    @ManyToOne
    private Wallet toWallet;
    @Enumerated(EnumType.STRING)
    private TransactionCategory category;
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUpdated;
    public Transaction() {
    }
    private BigDecimal fromBalance = BigDecimal.ZERO;
    private BigDecimal toBalance = BigDecimal.ZERO;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Wallet getFromWallet() {
        return fromWallet;
    }

    public void setFromWallet(Wallet fromWallet) {
        this.fromWallet = fromWallet;
    }

    public Wallet getToWallet() {
        return toWallet;
    }

    public void setToWallet(Wallet toWallet) {
        this.toWallet = toWallet;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }

    public Date getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(Date lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public BigDecimal getFromBalance() {
        return fromBalance;
    }

    public void setFromBalance(BigDecimal fromBalance) {
        this.fromBalance = fromBalance;
    }

    public BigDecimal getToBalance() {
        return toBalance;
    }

    public void setToBalance(BigDecimal toBalance) {
        this.toBalance = toBalance;
    }

    public void selfDeposit(){

    }
}
