package com.example.performance_reservation.application.performance;


import com.example.performance_reservation.domain.performance.dto.PerformanceInfo;
import com.example.performance_reservation.domain.performance.domain.Performance;
import com.example.performance_reservation.domain.performance.domain.PerformanceDetail;
import com.example.performance_reservation.domain.performance.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PerformanceFacade {
    private final PerformanceService performanceService;

    public List<PerformanceInfo> getPerformanceInfo(
        final LocalDateTime startDate,
        final LocalDateTime endDate
    ) {
        List<PerformanceDetail> details = performanceService.getDetailsByIntervalDate(startDate, endDate);
        List<Long> performanceIds = performanceService.getIdSet(details);
        List<Performance> performancesMeta = performanceService.getMeta(performanceIds);
        return PerformanceInfo.convert(performancesMeta, details);
    }

    public PerformanceDetail getPerformanceDetail(final int performanceId) {
        return performanceService.getPerformanceDetail(performanceId);
    }



}