package com.example.performance_reservation.controller.performance.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record PerformanceInfoResponse(

    long performanceId,
    long detailId,

    String performanceName,
    LocalDate startDate,
    int remainNums
) {
}
