package com.example.performance_reservation.controller.performance.response;

import java.util.List;

public record PerformanceDetailResponse(
    int performance_id,
    String performance_name,
    List<Integer> remain_seats
) {
}
