package com.example.performance_reservation.infrastructure.performance;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.dto.PerformanceInfo;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface JpaPerformanceRepository extends Repository<Performance, Long> {
    Optional<Performance> findById(final long id);
    List<Performance> findByIdIn(final List<Long> ids);
    Performance save(Performance entity);
}