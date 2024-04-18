package com.example.performance_reservation.error;

public record ErrorResponse(
    String errorCode,
    String message
) {
}
