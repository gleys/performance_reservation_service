package com.example.performance_reservation.domain.performance.application;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.SeatInfo;
import com.example.performance_reservation.domain.performance.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    public List<Performance> getMeta(final List<Long> ids) {
        return performanceRepository.findByIdIn(ids);
    }

    public List<PerformanceDetail> getDetailsByIntervalDate(final LocalDateTime startDate, final LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) throw new IllegalArgumentException("조회 시작 날짜는 종료 날짜 이전 이어야 입니다.");
        return performanceRepository.findAllByDateBetween(startDate, endDate);
    }

    public PerformanceDetail getPerformanceDetail(final int performanceId) {
        return performanceRepository.findPerformanceDetailById(performanceId);
    }

    public List<SeatInfo> getSeatInfo(final long performanceDetailId) {
        return performanceRepository.findByPerformanceDetailId(performanceDetailId);
    }

    public List<Long> getIdSet(List<PerformanceDetail> details) {
        return details.stream()
                       .sorted(Comparator.comparing(PerformanceDetail::getStartDate))
                       .map(PerformanceDetail::getPerformanceId)
                       .distinct()
                       .toList();

    }


}
