package com.example.performance_reservation.infrastructure.performance;

import com.example.performance_reservation.domain.performance.domain.PerformanceDetail;
import org.springframework.data.repository.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JpaPerformanceDetailRepository extends Repository<PerformanceDetail, Long> {
    Optional<PerformanceDetail> findById(final long ids);
    List<PerformanceDetail> findByStartDateBetween(final LocalDateTime startDate, final LocalDateTime endDate);
}
