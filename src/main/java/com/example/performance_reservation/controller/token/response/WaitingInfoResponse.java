package com.example.performance_reservation.controller.token.response;

public record QueueingInfoResponse(
    int user_id,
    int standby_nums
) {
}