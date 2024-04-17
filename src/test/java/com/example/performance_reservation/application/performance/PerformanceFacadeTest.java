package com.example.performance_reservation.application.performance;

import com.example.performance_reservation.infrastructure.performance.FakePerformanceRepository;
import com.example.performance_reservation.domain.performance.service.PerformanceService;
import com.example.performance_reservation.domain.performance.dto.PerformanceInfo;
import com.example.performance_reservation.infrastructure.performance.FakeSeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PerformanceReadServiceTest {

    private FakePerformanceRepository performanceRepository;
    private FakeSeatRepository seatRepository;
    private PerformanceService performanceService;
    private PerformanceReadService performanceReadService;
    @BeforeEach
    void init() {
        this.performanceRepository = new FakePerformanceRepository();
        this.seatRepository = new FakeSeatRepository();
        this.performanceService = new PerformanceService(this.performanceRepository, this.seatRepository);
        this.performanceReadService = new PerformanceReadService(this.performanceService);
    }

    @Test
    void 특정_시기_간격의_공연_정보를_조회한다() {
        // given
        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate future = LocalDate.now().plusDays(5);

        // when
        List<PerformanceInfo> performanceInfo = this.performanceReadService.getPerformanceInfo(yesterday, future);

        // then
        assertThat(performanceInfo.get(0).startDate()).isAfterOrEqualTo(yesterday);
        assertThat(performanceInfo.get(performanceInfo.size() - 1).startDate()).isBeforeOrEqualTo(future);

    }

}