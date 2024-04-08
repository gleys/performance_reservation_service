package com.example.performance_reservation.infrastructure.performance;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.SeatInfo;
import com.example.performance_reservation.domain.performance.repository.PerformanceRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public class PerformanceRepositoryImpl implements PerformanceRepository {
    @Override
    public List<Performance> findByIdIn(final List<Long> ids) {
        return null;
    }

    @Override
    public PerformanceDetail findPerformanceDetailById(final long ids) {
        return null;
    }

    @Override
    public List<PerformanceDetail> findAllByDateBetween(final LocalDateTime startDate, final LocalDateTime endDate) {
        return null;
    }

    @Override
    public List<SeatInfo> findByPerformanceDetailId(final long performanceDetailId) {
        return null;
    }
}
