package com.example.performance_reservation.controller.performance.response;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Builder
public record PerformanceInfoResponse(

    long performanceId,
    long detailId,
    String performanceName,
    String performer,
    LocalDate startDate,
    int amountSeats,
    int remainSeats

) {
}
