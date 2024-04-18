package com.example.performance_reservation.infrastructure.performance;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class PerformanceRepositoryImpl implements PerformanceRepository {
    private final JpaPerformanceRepository performanceRepository;
    private final JpaPerformanceDetailRepository performanceDetailRepository;

    @Override
    public List<Performance> getPerformances(final List<Long> ids) {
        return performanceRepository.findByIdIn(ids);
    }

    @Override
    public Optional<Performance> getPerformance(final long id) {
        return performanceRepository.findById(id);
    }

    @Override
    public Optional<PerformanceDetail> getPerformanceDetailWithLock(final long ids) {
        return performanceDetailRepository.findByIdWithPessimisticLock(ids);

    }

    @Override
    public Optional<PerformanceDetail> getPerformanceDetail(final long id) {
        return performanceDetailRepository.findById(id);
    }

    @Override
    public List<PerformanceDetail> getPerformanceDetailByDate(final LocalDate startDate, final LocalDate endDate) {
        return performanceDetailRepository.findByStartDateBetween(startDate, endDate);
    }

    @Override
    public PerformanceDetail save(final PerformanceDetail entity) {
        return performanceDetailRepository.save(entity);
    }

    public Performance save(final Performance entity) {
        return performanceRepository.save(entity);
    }

}