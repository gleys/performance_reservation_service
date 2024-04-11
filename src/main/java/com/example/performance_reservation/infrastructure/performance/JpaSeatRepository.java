package com.example.performance_reservation.infrastructure.performance;

import com.example.performance_reservation.domain.performance.Seat;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface JpaSeatRepository extends Repository<Seat, Long> {
    List<Seat> findByPerformanceDetailId(final long performanceDetailId);
    Seat save(Seat entity);
}