package com.example.performance_reservation.infrastructure.performance;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.Seat;
import com.example.performance_reservation.domain.performance.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
@Repository
public class PerformanceRepositoryImpl implements PerformanceRepository {
    private final JpaPerformanceRepository performanceRepository;
    private final JpaPerformanceDetailRepository performanceDetailRepository;
    private final JpaSeatRepository seatRepository;

    @Override
    public List<Performance> findByIdIn(final List<Long> ids) {
        return performanceRepository.findByIdIn(ids);
    }

    @Override
    public PerformanceDetail findPerformanceDetailById(final long ids) {
        return performanceDetailRepository.findById(ids)
                       .orElseThrow(() -> new IllegalArgumentException("존재 하지 않는 공연 id 입니다."));
    }

    @Override
    public List<PerformanceDetail> findByStartDateBetween(final LocalDateTime startDate, final LocalDateTime endDate) {
        return performanceDetailRepository.findByStartDateBetween(startDate, endDate);
    }

    @Override
    public List<Seat> findByPerformanceDetailId(final long performanceDetailId) {
        return seatRepository.findByPerformanceDetailId(performanceDetailId);
    }

    @Override
    public PerformanceDetail save(final PerformanceDetail entity) {
        return performanceDetailRepository.save(entity);
    }

    @Override
    public Seat save(final Seat entity) {
        return seatRepository.save(entity);
    }
}