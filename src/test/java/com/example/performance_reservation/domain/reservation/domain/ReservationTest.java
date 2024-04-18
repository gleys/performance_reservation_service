package com.example.performance_reservation.domain.reservation.domain;

import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.ReservationState;
import com.example.performance_reservation.domain.reservation.dto.Bill;
import com.example.performance_reservation.domain.reservation.exception.IllegalReservationStateException;
import com.example.performance_reservation.domain.reservation.exception.ReservationTimeExpiredException;
import com.example.performance_reservation.domain.reservation.exception.errorcode.ReservationErrorCode;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationTest {

    @Test
    void 임시_예약_생성_시_5_분후_만료된다() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveMinuteLater = now.plusMinutes(5);
        Bill historyDto = new Bill(
                1, 1, 1,"test", "test", LocalDate.now().plusWeeks(1), now, 10000);

        // when
        Reservation history = new Reservation(historyDto);

        // then
        assertThat(history.getExpiredAt()).isEqualTo(fiveMinuteLater);
    }
    
    @Test
    void 결제_시간이_지나거나_임시예약_상태가_아니면_예외를_던진다() {
        // given
        LocalDateTime past = LocalDateTime.now().minusMinutes(10);
        Bill expiredDto = new Bill(
                1, 1, 1, "test1", "test1", LocalDate.now().plusWeeks(1), past, 10000);
        Bill normalDto = new Bill(
                1, 2, 1, "test2", "test2", LocalDate.now().plusWeeks(1), LocalDateTime.now(), 10000);

        Reservation expiredHistory = new Reservation(expiredDto);
        Reservation paidHistory = new Reservation(normalDto);
        paidHistory.pay();

        // when & then
        assertThatThrownBy(expiredHistory::isValid)
                .isInstanceOf(ReservationTimeExpiredException.class)
                .hasMessage(ReservationErrorCode.RESERVATION_TIME_EXPIRED.getMessage());

        assertThatThrownBy(paidHistory::isValid)
                .isInstanceOf(IllegalReservationStateException.class)
                .hasMessage(ReservationErrorCode.RESERVATION_STATE_INVALID.getMessage());
    }

    @Test
    void 취소_처리시_현재_시간이_만료시간_이전이면_임시_예약_상태로_변경한다() {
        // given
        Bill normalDto = new Bill(
                1, 2, 1, "test2", "test2", LocalDate.now().plusWeeks(1), LocalDateTime.now(), 10000);

        Reservation paidHistory = new Reservation(normalDto);
        paidHistory.pay();

        // when
        paidHistory.cancel();

        // then
        assertThat(paidHistory.getState()).isEqualTo(ReservationState.PENDING);
    }

    @Test
    void 취소_처리시_현재_시간이_만료시간_이후면_만료_상태로_변경한다() {
        // given
        Bill expiredDto = new Bill(
                1, 2, 1, "test2", "test2", LocalDate.now().plusWeeks(1), LocalDateTime.now().minusMinutes(5), 10000);

        Reservation expiredHistory = new Reservation(expiredDto);

        // when
        expiredHistory.cancel();

        // then
        assertThat(expiredHistory.getState()).isEqualTo(ReservationState.EXPIRED);
    }


}