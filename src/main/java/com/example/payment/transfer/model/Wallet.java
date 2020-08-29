package com.example.payment.transfer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="wallet")
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // to set as autoincrement
    private Long id;

    private BigDecimal availableBalance=BigDecimal.ZERO;

    @JsonIgnore
    @OneToOne(mappedBy = "wallet")
    private User user;

    public Wallet() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getAvailableBalance() {
        return availableBalance;
    }

    public void setAvailableBalance(BigDecimal availableBalance) {
        this.availableBalance = availableBalance;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    public void deposit(BigDecimal amount) {
        this.availableBalance.add(amount);
    }
    public void withdraw(BigDecimal amount) {
        this.availableBalance.subtract(amount);
    }
}
