package com.example.performance_reservation.controller.reservation.response;

import com.example.performance_reservation.domain.reservation.HistoryState;

import java.time.LocalDateTime;

public record ReservationInfoResponse(
    int user_id,
    int history_id,
    int performance_id,
    HistoryState state,
    LocalDateTime reservation_date
) {
}
