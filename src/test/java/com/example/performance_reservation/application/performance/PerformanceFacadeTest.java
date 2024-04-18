package com.example.performance_reservation.application.performance;

import com.example.performance_reservation.domain.reservation.service.ReservationService;
import com.example.performance_reservation.infrastructure.performance.FakePerformanceRepository;
import com.example.performance_reservation.domain.performance.service.PerformanceService;
import com.example.performance_reservation.domain.performance.dto.PerformanceInfo;
import com.example.performance_reservation.infrastructure.reservation.FakeReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PerformanceFacadeTest {

    private FakePerformanceRepository performanceRepository;
    private FakeReservationRepository reservationRepository;
    private PerformanceService performanceService;
    private ReservationService reservationService;
    private PerformanceFacade performanceFacade;
    @BeforeEach
    void init() {
        this.performanceRepository = new FakePerformanceRepository();
        this.reservationRepository = new FakeReservationRepository();

        this.reservationService = new ReservationService(reservationRepository);
        this.performanceService = new PerformanceService(this.performanceRepository);

        this.performanceFacade = new PerformanceFacade(this.performanceService, reservationService);
    }

    @Test
    void 특정_시기_간격의_공연_정보를_조회한다() {
        // given
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate future = LocalDate.now().plusDays(5);

        // when
        List<PerformanceInfo> performanceInfo = this.performanceFacade.getPerformanceInfo(yesterday, future);

        // then
        assertThat(performanceInfo.get(0).startDate()).isAfterOrEqualTo(yesterday);
        assertThat(performanceInfo.get(performanceInfo.size() - 1).startDate()).isBeforeOrEqualTo(future);

    }

}