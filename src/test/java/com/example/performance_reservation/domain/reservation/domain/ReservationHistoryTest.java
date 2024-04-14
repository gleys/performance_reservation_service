package com.example.performance_reservation.domain.reservation.domain;

import com.example.performance_reservation.domain.reservation.HistoryState;
import com.example.performance_reservation.domain.reservation.ReservationHistory;
import com.example.performance_reservation.domain.reservation.dto.ReservationHistoryDto;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationHistoryTest {

    @Test
    void 임시_예약_생성_시_5_분후_만료된다() {
        // given
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime fiveMinuteLater = now.plusMinutes(5);
        ReservationHistoryDto historyDto = new ReservationHistoryDto(
                1, 1, "test", "test", LocalDateTime.now().plusWeeks(1), now, 10000);

        // when
        ReservationHistory history = new ReservationHistory(historyDto);

        // then
        assertThat(history.getExpiredAt()).isEqualTo(fiveMinuteLater);
    }
    
    @Test
    void 결제_시간이_지나거나_임시예약_상태가_아니면_예외를_던진다() {
        // given
        LocalDateTime past = LocalDateTime.now().minusMinutes(10);
        ReservationHistoryDto expiredDto = new ReservationHistoryDto(
                1, 1, "test1", "test1", LocalDateTime.now().plusWeeks(1), past, 10000);
        ReservationHistoryDto normalDto = new ReservationHistoryDto(
                1, 2, "test2", "test2", LocalDateTime.now().plusWeeks(1), LocalDateTime.now(), 10000);

        ReservationHistory expiredHistory = new ReservationHistory(expiredDto);
        ReservationHistory paidHistory = new ReservationHistory(normalDto);
        paidHistory.complete();

        // when & then
        assertThatThrownBy(expiredHistory::isValid)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("임시 예약 시간이 만료 되었습니다.");

        assertThatThrownBy(paidHistory::isValid)
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("임시 예약 상태가 아닙니다.");
    }

    @Test
    void 취소_처리시_현재_시간이_만료시간_이전이면_임시_예약_상태로_변경한다() {
        // given
        ReservationHistoryDto normalDto = new ReservationHistoryDto(
                1, 2, "test2", "test2", LocalDateTime.now().plusWeeks(1), LocalDateTime.now(), 10000);

        ReservationHistory paidHistory = new ReservationHistory(normalDto);
        paidHistory.complete();

        // when
        paidHistory.cancel();

        // then
        assertThat(paidHistory.getState()).isEqualTo(HistoryState.PENDING);
    }

    @Test
    void 취소_처리시_현재_시간이_만료시간_이후면_만료_상태로_변경한다() {
        // given
        ReservationHistoryDto expiredDto = new ReservationHistoryDto(
                1, 2, "test2", "test2", LocalDateTime.now().plusWeeks(1), LocalDateTime.now().minusMinutes(5), 10000);

        ReservationHistory expiredHistory = new ReservationHistory(expiredDto);

        // when
        expiredHistory.cancel();

        // then
        assertThat(expiredHistory.getState()).isEqualTo(HistoryState.EXPIRED);
    }


}