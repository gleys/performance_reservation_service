package com.example.performance_reservation.domain.performance.dto;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record PerformanceInfo(
    long performance_id,
    String title,
    String performer,
    int remain_nums,
    LocalDateTime start_date

) {
    public static List<PerformanceInfo> convert(
        final List<Performance> performancesMeta,
        final List<PerformanceDetail> details
    ) {
        Map<Long, Performance> performanceMap = performancesMeta.stream()
                                             .collect(Collectors.toMap(Performance::getId, meta -> meta));

        return details.stream().map(detail -> {
            long performanceId = detail.getPerformanceId();
            Performance performance = performanceMap.get(performanceId);
            return new PerformanceInfo(
                    performanceId,
                    performance.getTitle(),
                    performance.getPerformer(),
                    detail.getRemainSeatNums(),
                    detail.getStartDate()
            );
        }).toList();
    }
}
