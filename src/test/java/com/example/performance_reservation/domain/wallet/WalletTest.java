package com.example.performance_reservation.domain.wallet;

import com.example.performance_reservation.domain.wallet.exception.AmountInsufficientException;
import com.example.performance_reservation.domain.wallet.exception.AmountInvalidException;
import com.example.performance_reservation.domain.wallet.exception.errorcode.WalletErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class WalletTest {

    @Test
    void 잔고가_부족하면_예외를_던진다() {
        // given
        Wallet wallet = new Wallet(1, 1, 1000);

        // when & then
        assertThatThrownBy(() -> wallet.pay(2000))
                .isInstanceOf(AmountInsufficientException.class)
                .hasMessage(WalletErrorCode.AMOUNT_INSUFFICIENT.getMessage());
    }

    @Test
    void 충전_금액이_0포인트_이하일_때_예외를_던진다() {
        // given
        Wallet wallet = new Wallet(1, 1, 1000);

        // when & then
        assertThatThrownBy(() -> wallet.charge(0))
                .isInstanceOf(AmountInvalidException.class)
                .hasMessage(WalletErrorCode.AMOUNT_INSUFFICIENT.getMessage());

        assertThatThrownBy(() -> wallet.charge(-100))
                .isInstanceOf(AmountInvalidException.class)
                .hasMessage(WalletErrorCode.AMOUNT_INSUFFICIENT.getMessage());
    }

}