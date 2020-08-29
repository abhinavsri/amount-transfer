package com.example.payment.transfer.repository;

import com.example.payment.transfer.model.Transaction;
import com.example.payment.transfer.model.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findAllByFromWalletOrToWallet(Wallet wallet);
}
