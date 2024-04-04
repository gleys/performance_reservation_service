package com.example.performance_reservation.controller.wallet.request;

public record ChargeBalanceRequest(
    int user_id,
    int amount
) {
}
