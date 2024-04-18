package com.example.performance_reservation.infrastructure.performance;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.repository.PerformanceRepository;

import java.time.Duration;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FakePerformanceRepository implements PerformanceRepository {
    private Map<Long, Performance> performanceMap;

    private Map<Long, PerformanceDetail> performanceDetailMap;
    public FakePerformanceRepository() {
        Map<Long, Performance> map1 = new HashMap<>();
        map1.put(1L, new Performance(1, "test1", "test1", 10000));
        map1.put(2L, new Performance(2, "test2", "test2", 20000));
        map1.put(3L, new Performance(3, "test3", "test3", 30000));
        map1.put(4L, new Performance(4, "test4", "test4", 40000));
        map1.put(5L, new Performance(5, "test5", "test5", 50000));
        this.performanceMap = map1;

        Map<Long, PerformanceDetail> map2 = new HashMap<>();
        map2.put(1L, new PerformanceDetail(1, 1, 30, 30, LocalDate.now().plusWeeks(1)));
        map2.put(2L, new PerformanceDetail(2, 1, 30, 0, LocalDate.now().minusDays(1)));
        map2.put(3L, new PerformanceDetail(3, 2, 30, 15, LocalDate.now().plusWeeks(1)));
        map2.put(4L, new PerformanceDetail(4, 3, 30, 30, LocalDate.now().plusWeeks(1)));
        map2.put(5L, new PerformanceDetail(5, 4, 30, 30, LocalDate.now().plusWeeks(1)));
        map2.put(6L, new PerformanceDetail(6, 5, 30, 30, LocalDate.now().plusWeeks(1)));
        this.performanceDetailMap = map2;
    }

    @Override
    public List<Performance> getPerformances(final List<Long> ids) {
        return ids.stream().filter(i -> performanceMap.containsKey(i))
                       .map(i -> performanceMap.get(i)).toList();
    }

    @Override
    public Optional<Performance> getPerformance(final long id) {
        return Optional.of(this.performanceMap.get(id));
    }

    @Override
    public Optional<PerformanceDetail> getPerformanceDetailWithLock(final long id) {
        return Optional.of(performanceDetailMap.get(id));
    }

    @Override
    public Optional<PerformanceDetail> getPerformanceDetail(final long id) {
        return Optional.empty();
    }

    @Override
    public List<PerformanceDetail> getPerformanceDetailByDate(final LocalDate startDate, final LocalDate endDate) {
        if (startDate.isEqual(endDate)) {
            PerformanceDetail detail1 = new PerformanceDetail(1, 1, 50, 0, startDate);
            PerformanceDetail detail2 = new PerformanceDetail(2, 1, 45, 30, endDate);
            PerformanceDetail detail3 = new PerformanceDetail(3, 2, 50, 38, endDate);
            PerformanceDetail detail4 = new PerformanceDetail(4, 3, 50, 0, startDate);
            PerformanceDetail detail5 = new PerformanceDetail(5, 3, 20, 10, endDate);
            PerformanceDetail detail6 = new PerformanceDetail(6, 4, 30, 30, startDate);

            return List.of(detail1, detail2, detail3, detail4, detail5, detail6);
        }

        long days = Duration.between(startDate.atStartOfDay(), endDate.atStartOfDay()).toDays();

        PerformanceDetail detail1 = new PerformanceDetail(1, 1, 50, 0, startDate);
        PerformanceDetail detail2 = new PerformanceDetail(2, 1, 45, 30, startDate.plusDays(days));
        PerformanceDetail detail3 = new PerformanceDetail(3, 2, 50, 38, startDate.plusDays(days - 1));
        PerformanceDetail detail4 = new PerformanceDetail(4, 3, 50, 0, startDate);
        PerformanceDetail detail5 = new PerformanceDetail(5, 3, 20, 10, endDate);
        PerformanceDetail detail6 = new PerformanceDetail(6, 4, 30, 30, startDate);

        return List.of(detail1, detail2, detail3, detail4, detail5, detail6);
    }

    @Override
    public PerformanceDetail save(final PerformanceDetail entity) {
        long id = entity.getId();
        performanceDetailMap.replace(id, entity);
        return entity;
    }

}
