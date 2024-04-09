package com.example.performance_reservation.application.performance;

import com.example.performance_reservation.domain.performance.service.FakePerformanceRepository;
import com.example.performance_reservation.domain.performance.service.PerformanceService;
import com.example.performance_reservation.domain.performance.dto.PerformanceInfo;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PerformanceFacadeTest {

    @Test
    void 특정_시기_간격의_공연_정보를_조회한다() {
        // given
        FakePerformanceRepository fakePerformanceRepository = new FakePerformanceRepository();
        PerformanceService performanceService = new PerformanceService(fakePerformanceRepository);
        PerformanceFacade performanceFacade = new PerformanceFacade(performanceService);
        LocalDateTime yesterday = LocalDateTime.now().minusDays(1);
        LocalDateTime future = LocalDateTime.now().plusDays(5);

        // when
        List<PerformanceInfo> performanceInfo = performanceFacade.getPerformanceInfo(yesterday, future);

        // then
        assertThat(performanceInfo.get(0).start_date()).isAfterOrEqualTo(yesterday);
        assertThat(performanceInfo.get(performanceInfo.size() - 1).start_date()).isBeforeOrEqualTo(future);

    }

}