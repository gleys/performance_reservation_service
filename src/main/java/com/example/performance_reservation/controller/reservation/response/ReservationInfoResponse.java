package com.example.performance_reservation.controller.reservation.response;

import com.example.performance_reservation.domain.reservation.ReservationState;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ReservationInfoResponse(
    long reservationId,
    long performanceDetailId,
    ReservationState state,
    LocalDateTime reservationDate
) {
}
