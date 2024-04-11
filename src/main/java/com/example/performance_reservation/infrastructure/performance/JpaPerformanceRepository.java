package com.example.performance_reservation.infrastructure.performance;

import com.example.performance_reservation.domain.performance.Performance;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface JpaPerformanceRepository extends Repository<Performance, Long> {
    List<Performance> findByIdIn(List<Long> ids);
}
