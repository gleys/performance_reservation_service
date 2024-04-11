package com.example.performance_reservation.controller.reservation;

import com.example.performance_reservation.controller.reservation.response.ReservationInfoResponse;
import com.example.performance_reservation.domain.reservation.HistoryState;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/reservations")
@RestController
public class ReservationController {

    @GetMapping
    public List<ReservationInfoResponse> getReservation(@RequestParam("user_id") final int userId) {
        return List.of(
            new ReservationInfoResponse(userId, 1, 1, HistoryState.PURCHASED, LocalDateTime.now()),
            new ReservationInfoResponse(userId, 2, 6, HistoryState.EXPIRED, LocalDateTime.now().minusDays(1)),
            new ReservationInfoResponse(userId, 3, 4, HistoryState.PENDING, LocalDateTime.now())
        );
    }

    @PostMapping("/{seat_id}")
    public void reserve(@PathVariable("seat_id") final long seatId) {
    }

    @PostMapping("/payments/{reservation_id}")
    public void pay(
        @PathVariable("reservation_id") int reservationId,
        @RequestParam("user_id") int userId) {
    }

    @PatchMapping("/payments/{reservation_id}")
    public void cancel(
        @PathVariable("reservation_id") int reservationId,
        @RequestParam("user_id") int userId) {
    }
}
