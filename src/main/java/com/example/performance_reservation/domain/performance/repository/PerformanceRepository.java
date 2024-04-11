package com.example.performance_reservation.domain.performance.repository;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.Seat;

import java.time.LocalDateTime;
import java.util.List;

public interface PerformanceRepository {
    List<Performance> findByIdIn(final List<Long> ids);
    PerformanceDetail findPerformanceDetailById(final long ids);
    List<PerformanceDetail> findByStartDateBetween(final LocalDateTime startDate, final LocalDateTime endDate);
    List<Seat> findByPerformanceDetailId(final long performanceDetailId);
    PerformanceDetail save(PerformanceDetail entity);
    Seat save(Seat entity);
}