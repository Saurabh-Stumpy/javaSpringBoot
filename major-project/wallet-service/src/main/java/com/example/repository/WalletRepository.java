package com.example.repository;

import com.example.models.Wallet;
import jakarta.transaction.Transactional;
import org.hibernate.annotations.processing.SQL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface WalletRepository extends JpaRepository<Wallet, Integer> {

    Wallet findByWalletId(String walletId);

    @Transactional
    @Modifying
    @Query("update Wallet w set w.balance = w.balance + :amount where w.walletId = :walletId")
    void updateWallet(String walletId, Long amount);
}
