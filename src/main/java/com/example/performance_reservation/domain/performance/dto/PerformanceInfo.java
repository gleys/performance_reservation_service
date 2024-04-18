package com.example.performance_reservation.domain.performance.dto;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Builder
public record PerformanceInfo(
    long performanceId,
    long detailId,
    String title,
    String performer,
    int remainSeats,
    int amountSeats,
    LocalDate startDate

) {
    public static List<PerformanceInfo> convert(
        final List<Performance> performancesMeta,
        final List<PerformanceDetail> details
    ) {
        Map<Long, Performance> performanceMap = performancesMeta.stream()
                                             .collect(Collectors.toMap(Performance::getId, meta -> meta));

        return details.stream().map(detail -> {
            long detailId = detail.getPerformanceId();
            Performance performance = performanceMap.get(detailId);
            return new PerformanceInfo(
                    performance.getId(),
                    detailId,
                    performance.getTitle(),
                    performance.getPerformer(),
                    detail.getRemainSeats(),
                    detail.getAmountSeats(),
                    detail.getStartDate()
            );
        }).toList();
    }

}