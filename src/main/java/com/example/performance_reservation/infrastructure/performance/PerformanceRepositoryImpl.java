package com.example.performance_reservation.infrastructure.performance;

import com.example.performance_reservation.domain.performance.domain.Performance;
import com.example.performance_reservation.domain.performance.domain.PerformanceDetail;
import com.example.performance_reservation.domain.performance.domain.SeatInfo;
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
    private final JpaSeatInfoRepository seatInfoRepository;

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
    public List<SeatInfo> findByPerformanceDetailId(final long performanceDetailId) {
        return seatInfoRepository.findByPerformanceDetailId(performanceDetailId);
    }
}