package com.example.performance_reservation.controller.reservation;

import com.example.performance_reservation.controller.reservation.response.ReservationInfoResponse;
import com.example.performance_reservation.domain.reservation.domain.HistoryState;
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

    @PostMapping("/{performance_id}")
    public void reserve(
        @PathVariable("performance_id") final int performanceId,
        @RequestParam("user_id") final int userId,
        @RequestParam("seat_no") final int seatNo) {
    }

    @PostMapping("/payments/{reservation_id}")
    public void pay(
        @PathVariable("reservation_id") int reservationId,
        @RequestParam("user_id") int userId) {
    }

    @DeleteMapping("/payments/{reservation_id}")
    public void cancel(
        @PathVariable("reservation_id") int reservationId,
        @RequestParam("user_id") int userId) {
    }
}
