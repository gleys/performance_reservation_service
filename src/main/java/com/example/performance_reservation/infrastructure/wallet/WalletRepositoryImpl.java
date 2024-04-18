package com.example.performance_reservation.infrastructure.wallet;

import com.example.performance_reservation.domain.wallet.PointHistory;
import com.example.performance_reservation.domain.wallet.Wallet;
import com.example.performance_reservation.domain.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class WalletRepositoryImpl implements WalletRepository {
    private final JpaWalletRepository walletRepository;
    private final JpaPointHistoryRepository pointHistoryRepository;

    @Override
    public Optional<Wallet> getWallet(final long userId) {
        return walletRepository.findByUserId(userId);
    }

    @Override
    public List<PointHistory> getPointHistory(final long userId) {
        return pointHistoryRepository.findByUserId(userId);
    }

    @Override
    public void recordHistory(final PointHistory entity) {
        pointHistoryRepository.save(entity);
    }

    @Override
    public Wallet save(final Wallet entity) {
        return walletRepository.save(entity);
    }
}
