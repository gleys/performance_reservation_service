package com.example.performance_reservation.domain.reservation.dto;

import java.time.LocalDateTime;

public record ReservationHistoryDto(
    long userId,
    long seatInfoId,
    String title,
    String performer,
    LocalDateTime startDate,
    LocalDateTime now,
    int price
) {

}
