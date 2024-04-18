package com.example.performance_reservation.domain.wallet.service;

import com.example.performance_reservation.domain.wallet.PointActivity;
import com.example.performance_reservation.domain.wallet.PointHistory;
import com.example.performance_reservation.domain.wallet.Wallet;
import com.example.performance_reservation.domain.wallet.exception.WalletNotFoundException;
import com.example.performance_reservation.domain.wallet.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Transactional
@Component
public class WalletService {
    private final WalletRepository walletRepository;

    public void chargePoint(final long userId, final int amount, final PointActivity activity) {
        Wallet wallet = this.getWallet(userId);
        wallet.charge(amount);
        PointHistory history = PointHistory.record(userId, amount, activity);
        walletRepository.recordHistory(history);
    }

    public void usePoint(final long userId, final int amount, final PointActivity activity) {
        Wallet wallet = this.getWallet(userId);
        wallet.pay(amount);
        PointHistory history = PointHistory.record(userId, amount, activity);
        walletRepository.recordHistory(history);
    }


    @Transactional(readOnly = true)
    public Wallet getWallet(final long userId) {
        return walletRepository.getWallet(userId)
               .orElseThrow(() -> WalletNotFoundException.EXCEPTION);
    }

    @Transactional(readOnly = true)
    public List<PointHistory> getHistories(final long userId) {
        return walletRepository.getPointHistory(userId);
    }

}
