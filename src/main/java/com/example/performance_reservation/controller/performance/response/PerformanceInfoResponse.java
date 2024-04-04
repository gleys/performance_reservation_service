package com.example.performance_reservation.controller.performance.response;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public record PerformanceInfoResponse(

    int performance_id,
    String performance_name,
    LocalDate start_date,
    int remain_nums
) {
}
