package com.example.performance_reservation.controller.token.response;

public record TokenResponse(
    int user_id,
    int performance_id,
    int standby_nums
) {
}