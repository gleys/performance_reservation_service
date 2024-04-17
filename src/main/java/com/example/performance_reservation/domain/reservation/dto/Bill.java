package com.example.performance_reservation.domain.reservation.dto;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservationTable(
    long userId,
    long seatId,
    String title,
    String performer,
    LocalDateTime startDate,
    LocalDateTime now,
    int price
) {
}