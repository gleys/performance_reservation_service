package com.example.performance_reservation.controller.performance.response;

import java.util.List;

public record PerformanceDetailResponse(
    long performanceId,
    long performanceDetailId,
    List<Integer> remainSeats
) {
}
