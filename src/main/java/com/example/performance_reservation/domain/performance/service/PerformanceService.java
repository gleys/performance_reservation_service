package com.example.performance_reservation.domain.performance.service;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.exception.InvalidDateConditionException;
import com.example.performance_reservation.domain.performance.exception.PerformanceNotFoundException;
import com.example.performance_reservation.domain.performance.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Component
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    public List<Performance> getMetaInfo(final List<Long> ids) {
        return performanceRepository.getPerformances(ids);
    }

    public Performance getMetaInfo(final long id) {
        return performanceRepository.getPerformance(id)
                       .orElseThrow(() -> PerformanceNotFoundException.Exception);
    }

    public List<PerformanceDetail> getDetailsByIntervalDate(final LocalDate startDate, final LocalDate endDate) {
        if (startDate.isAfter(endDate)) throw InvalidDateConditionException.Exception;
        return performanceRepository.getPerformanceDetailByDate(startDate, endDate);
    }

    public PerformanceDetail getPerformanceDetail(final long performanceDetailId) {
        return performanceRepository.getPerformanceDetail(performanceDetailId)
                       .orElseThrow(() -> PerformanceNotFoundException.Exception);
    }

    public PerformanceDetail getReservablePerformanceDetail(final long performanceId) {
        PerformanceDetail performanceDetail = performanceRepository.getPerformanceDetailWithLock(performanceId)
                                          .orElseThrow(() -> PerformanceNotFoundException.Exception);
        performanceDetail.isReservable();
        return performanceDetail;
    }

    public List<Long> getIdSet(List<PerformanceDetail> details) {
        return details.stream()
                       .sorted(Comparator.comparing(PerformanceDetail::getStartDate))
                       .map(PerformanceDetail::getPerformanceId)
                       .distinct()
                       .toList();
    }


    public void decreaseRemainSeats(final PerformanceDetail detail) {
        detail.decreaseRemainSeats();
        performanceRepository.save(detail);
    }

    public void increaseRemainSeats(final long performanceDetailId) {
        PerformanceDetail performanceDetail = this.getPerformanceDetail(performanceDetailId);
        performanceDetail.increaseRemainSeats();
        performanceRepository.save(performanceDetail);
    }

}
