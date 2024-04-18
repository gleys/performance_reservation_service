package com.example.performance_reservation.application.performance.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record PerformanceOverview(
    long performanceDetailId,
    int amountSeats,
    int remainSeats,
    List<Integer> availableSeats,
    LocalDate startDate
) {
}
