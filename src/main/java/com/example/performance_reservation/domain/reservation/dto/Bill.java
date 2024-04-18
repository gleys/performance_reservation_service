package com.example.performance_reservation.domain.reservation.dto;

import lombok.Builder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record Bill(
    long userId,
    long performanceDetailId,
    int seatNo,
    String title,
    String performer,
    LocalDate startDate,
    LocalDateTime now,
    int price
) {
}