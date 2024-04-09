package com.example.performance_reservation.domain.performance.repository;

import com.example.performance_reservation.domain.performance.domain.Performance;
import com.example.performance_reservation.domain.performance.domain.PerformanceDetail;
import com.example.performance_reservation.domain.performance.domain.SeatInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface PerformanceRepository {
    List<Performance> findByIdIn(final List<Long> ids);
    PerformanceDetail findPerformanceDetailById(final long ids);
    List<PerformanceDetail> findByStartDateBetween(final LocalDateTime startDate, final LocalDateTime endDate);
    List<SeatInfo> findByPerformanceDetailId(final long performanceDetailId);
}
