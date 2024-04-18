package com.example.performance_reservation.infrastructure.wallet;

import com.example.performance_reservation.domain.wallet.PointHistory;
import com.example.performance_reservation.domain.wallet.Wallet;
import com.example.performance_reservation.domain.wallet.repository.WalletRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FakeWalletRepository implements WalletRepository {
    private List<Wallet> wallets = new ArrayList<>();
    private List<PointHistory> histories = new ArrayList<>();

    @Override
    public Optional<Wallet> getWallet(final long userId) {
        return wallets.stream().filter(wallet -> wallet.getUserId() == userId)
                .findFirst();
    }

    @Override
    public List<PointHistory> getPointHistory(final long userId) {
        return histories.stream().filter(history -> history.getUserId() == userId).toList();
    }

    @Override
    public void recordHistory(final PointHistory entity) {
        histories.add(entity);
    }

    @Override
    public Wallet save(final Wallet entity) {
        wallets.add(entity);
        return entity;
    }
}
