package com.example.performance_reservation.domain.performance.repository;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface PerformanceRepository {
    List<Performance> getPerformances(final List<Long> ids);
    Optional<Performance> getPerformance(final long id);
    Optional<PerformanceDetail> getPerformanceDetailWithLock(final long id);
    Optional<PerformanceDetail> getPerformanceDetail(final long id);
    List<PerformanceDetail> getPerformanceDetailByDate(final LocalDate startDate, final LocalDate endDate);
    PerformanceDetail save(PerformanceDetail entity);

}