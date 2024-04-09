package com.example.performance_reservation.infrastructure.performance;

import com.example.performance_reservation.domain.performance.domain.SeatInfo;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface JpaSeatInfoRepository extends Repository<SeatInfo, Long> {
    List<SeatInfo> findByPerformanceDetailId(final long performanceDetailId);
}