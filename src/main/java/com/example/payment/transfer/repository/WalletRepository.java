package com.example.payment.transfer.repository;

import com.example.payment.transfer.model.User;
import com.example.payment.transfer.model.Wallet;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.LockModeType;
import java.util.Optional;

@Repository
public interface WalletRepository extends JpaRepository<Wallet,Long> {
    Optional<Wallet> findByUserIdAndId(Long userId, Long walletId);
    @Lock(LockModeType.PESSIMISTIC_WRITE)

    @Query("select wallet from Wallet wallet where wallet.id= :id")
    Optional<Wallet> getWallet(@Param("id") Long id);

}