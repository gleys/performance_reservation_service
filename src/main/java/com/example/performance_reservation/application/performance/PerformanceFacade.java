package com.example.performance_reservation.application.performance;


import com.example.performance_reservation.domain.performance.Seat;
import com.example.performance_reservation.domain.performance.dto.PerformanceInfo;
import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PerformanceReadService {
    private final PerformanceService performanceService;

    public List<PerformanceInfo> getPerformanceInfo(
        final LocalDate startDate,
        final LocalDate endDate
    ) {
        List<PerformanceDetail> details = performanceService.getDetailsByIntervalDate(startDate, endDate);
        List<Long> performanceIds = performanceService.getIdSet(details);
        List<Performance> performancesMeta = performanceService.getMetaInfo(performanceIds);
        return PerformanceInfo.convert(performancesMeta, details);
    }

    public PerformanceDetail getPerformanceDetail(final long performanceId) {
        return performanceService.getReservablePerformanceDetail(performanceId);
    }


}