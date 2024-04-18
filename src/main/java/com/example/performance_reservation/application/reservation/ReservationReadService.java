package com.example.performance_reservation.application.reservation;

import com.example.performance_reservation.controller.reservation.response.ReservationInfoResponse;
import com.example.performance_reservation.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ReservationReadService {
    private final ReservationService reservationService;

    public List<ReservationInfoResponse> getReservation(final long userId) {
        return reservationService.getMyReservations(userId).stream()
                       .map(reservation -> ReservationInfoResponse.builder()
                                       .reservationId(reservation.getId())
                                       .performanceDetailId(reservation.getPerformanceDetailId())
                                       .state(reservation.getState())
                                       .reservationDate(reservation.getCreatedAt()).build()).toList();
    }
}
