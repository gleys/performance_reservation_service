package com.example.performance_reservation.domain.performance.application;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.SeatInfo;
import com.example.performance_reservation.domain.performance.repository.PerformanceRepository;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

public class FakePerformanceRepository implements PerformanceRepository {
    @Override
    public List<Performance> findByIdIn(final List<Long> ids) {
        return ids.stream().map(id ->
                          new Performance(id, "test" + id, "tester" + id, 50000)).toList();
    }

    @Override
    public PerformanceDetail findPerformanceDetailById(final long ids) {
        return null;
    }

    @Override
    public List<PerformanceDetail> findAllByDateBetween(final LocalDateTime startDate, final LocalDateTime endDate) {
        if (startDate.isEqual(endDate)) {
            PerformanceDetail detail1 = new PerformanceDetail(1, 1, 50, 0, startDate);
            PerformanceDetail detail2 = new PerformanceDetail(2, 1, 45, 30, endDate);
            PerformanceDetail detail3 = new PerformanceDetail(3, 2, 50, 38, endDate);
            PerformanceDetail detail4 = new PerformanceDetail(4, 3, 50, 0, startDate);
            PerformanceDetail detail5 = new PerformanceDetail(5, 3, 20, 10, endDate);
            PerformanceDetail detail6 = new PerformanceDetail(6, 4, 30, 30, startDate);

            return List.of(detail1, detail2, detail3, detail4, detail5, detail6);
        }
        long days = Duration.between(startDate, endDate).toDays();

        PerformanceDetail detail1 = new PerformanceDetail(1, 1, 50, 0, startDate);
        PerformanceDetail detail2 = new PerformanceDetail(2, 1, 45, 30, startDate.plusDays(days));
        PerformanceDetail detail3 = new PerformanceDetail(3, 2, 50, 38, startDate.plusDays(days - 1));
        PerformanceDetail detail4 = new PerformanceDetail(4, 3, 50, 0, startDate);
        PerformanceDetail detail5 = new PerformanceDetail(5, 3, 20, 10, endDate);
        PerformanceDetail detail6 = new PerformanceDetail(6, 4, 30, 30, startDate);

        return List.of(detail1, detail2, detail3, detail4, detail5, detail6);
    }

    @Override
    public List<SeatInfo> findByPerformanceDetailId(final long performanceDetailId) {
        return null;
    }
}
