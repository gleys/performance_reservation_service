package com.example.performance_reservation.controller.wallet.response;

import com.example.performance_reservation.domain.wallet.PointActivity;

import java.time.LocalDateTime;

public record PointHistoryResponse(
    int amount,
    PointActivity activity,
    LocalDateTime createdAt
) {
}
