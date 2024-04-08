package com.example.performance_reservation.domain.performance.repository;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.SeatInfo;

import java.time.LocalDateTime;
import java.util.List;

public interface PerformanceRepository {
    List<Performance> findByIdIn(final List<Long> ids);
    PerformanceDetail findPerformanceDetailById(final long ids);
    List<PerformanceDetail> findAllByDateBetween(final LocalDateTime startDate, final LocalDateTime endDate);
    List<SeatInfo> findByPerformanceDetailId(final long performanceDetailId);
}
