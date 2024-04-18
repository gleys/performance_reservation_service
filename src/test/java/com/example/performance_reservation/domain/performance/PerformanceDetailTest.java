package com.example.performance_reservation.domain.performance;

import com.example.performance_reservation.domain.performance.exception.InvalidSeatNoException;
import com.example.performance_reservation.domain.performance.exception.PerformanceNotReservableException;
import com.example.performance_reservation.domain.performance.exception.errorcode.PerformanceErrorCode;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class PerformanceDetailTest {

    @Test
    void 현재가_공연_시작날_이후이면_예외를_던진다() {
        // given
        PerformanceDetail pastPerformance = new PerformanceDetail(1, 1, 30, 30, LocalDate.now().minusDays(3));

        // when & then
        assertThatThrownBy(() -> pastPerformance.isReservable())
                .isInstanceOf(PerformanceNotReservableException.class)
                .hasMessage(PerformanceErrorCode.PERFORMANCE_NOT_RESERVABLE.getMessage());
    }

    @Test
    void 예약가능_좌석이_없으면_예외를_던진다() {
        // given
        PerformanceDetail pastPerformance = new PerformanceDetail(1, 1, 30, 0, LocalDate.now().minusDays(3));

        // when & then
        assertThatThrownBy(() -> pastPerformance.isReservable())
                .isInstanceOf(PerformanceNotReservableException.class)
                .hasMessage(PerformanceErrorCode.PERFORMANCE_NOT_RESERVABLE.getMessage());

    }

    @Test
    void 좌석_번호가_0_이하면_예외를_던진다() {
        // given
        PerformanceDetail pastPerformance = new PerformanceDetail(1, 1, 30, 30, LocalDate.now().minusDays(3));

        // when & then
        assertThatThrownBy(() -> pastPerformance.isValidSeatNo(0))
                .isInstanceOf(InvalidSeatNoException.class)
                .hasMessage(PerformanceErrorCode.SEAT_NO_INVALID.getMessage());

        assertThatThrownBy(() -> pastPerformance.isValidSeatNo(-1))
                .isInstanceOf(InvalidSeatNoException.class)
                .hasMessage(PerformanceErrorCode.SEAT_NO_INVALID.getMessage());
    }

    @Test
    void 좌석_번호가_50_보다_크면_예외를_던진다() {
        // given
        PerformanceDetail pastPerformance = new PerformanceDetail(1, 1, 30, 30, LocalDate.now().minusDays(3));

        // when & then
        assertThatThrownBy(() -> pastPerformance.isValidSeatNo(51))
                .isInstanceOf(InvalidSeatNoException.class)
                .hasMessage(PerformanceErrorCode.SEAT_NO_INVALID.getMessage());


    }

}