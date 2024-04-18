package com.example.performance_reservation.domain.wallet.service;

import com.example.performance_reservation.domain.wallet.PointActivity;
import com.example.performance_reservation.domain.wallet.PointHistory;
import com.example.performance_reservation.domain.wallet.Wallet;
import com.example.performance_reservation.domain.wallet.repository.WalletRepository;
import com.example.performance_reservation.infrastructure.wallet.FakeWalletRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.assertj.core.api.Assertions.assertThat;

class WalletServiceTest {
    private WalletService walletService;
    private WalletRepository walletRepository;
    @BeforeEach
    void init() {
        this.walletRepository = new FakeWalletRepository();
        this.walletService = new WalletService(this.walletRepository);
    }

    @Test
    void 포인트를_충전과_동시에_내역이_기록된다() {
        // given
        Wallet wallet = new Wallet(1, 1, 1000);
        walletRepository.save(wallet);

        // when
        walletService.chargePoint(1, 10000, PointActivity.CHARGE);

        // then
        PointHistory history = walletRepository.getPointHistory(1).get(0);
        wallet = walletRepository.getWallet(1).get();
        assertThat(wallet.getBalance()).isEqualTo(11000);
        assertThat(history.getUserId()).isEqualTo(1);
        assertThat(history.getAmount()).isEqualTo(10000);
        assertThat(history.getActivity()).isEqualTo(PointActivity.CHARGE);
    }

    @Test
    void 포인트_사용과_동시에_내역이_기록된다() {
        // given
        Wallet wallet = new Wallet(1, 1, 10000);
        walletRepository.save(wallet);

        // when
        walletService.usePoint(1, 10000, PointActivity.USE);

        // then
        PointHistory history = walletRepository.getPointHistory(1).get(0);
        wallet = walletRepository.getWallet(1).get();
        assertThat(wallet.getBalance()).isEqualTo(0);
        assertThat(history.getUserId()).isEqualTo(1);
        assertThat(history.getAmount()).isEqualTo(10000);
        assertThat(history.getActivity()).isEqualTo(PointActivity.USE);
    }



}