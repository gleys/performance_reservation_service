package com.example.performance_reservation.domain.wallet.repository;

import com.example.performance_reservation.domain.wallet.PointHistory;
import com.example.performance_reservation.domain.wallet.Wallet;

import java.util.List;
import java.util.Optional;

public interface WalletRepository {
    Optional<Wallet> getWallet(final long userId);
    List<PointHistory> getPointHistory(final long userId);
    void recordHistory(final PointHistory entity);
    Wallet save(final Wallet entity);

}
