package com.example.performance_reservation.controller.reservation;

import com.example.performance_reservation.controller.reservation.response.ReservationInfoResponse;
import com.example.performance_reservation.domain.reservation.HistoryState;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "예약")
@RequestMapping("/reservations")
@RestController
public class ReservationController {

    @Operation(summary = "예약 내역 조회")
    @GetMapping
    public List<ReservationInfoResponse> getReservation(@RequestParam("user_id") final int userId) {
        return List.of(
            new ReservationInfoResponse(userId, 1, 1, HistoryState.PURCHASED, LocalDateTime.now()),
            new ReservationInfoResponse(userId, 2, 6, HistoryState.EXPIRED, LocalDateTime.now().minusDays(1)),
            new ReservationInfoResponse(userId, 3, 4, HistoryState.PENDING, LocalDateTime.now())
        );
    }

    @Operation(summary = "좌석 임시 예약(예약 후 5분간 유효함)")
    @PostMapping("/{seat_id}")
    public void reserve(
            @RequestHeader final UUID token,
            @PathVariable("seat_id") final long seatId) {
    }

    @Operation(summary = "결제(임시 예약 상태에서만 가능함)")
    @PostMapping("/payments/{reservation_id}")
    public void pay(
        @PathVariable("reservation_id") int reservationId,
        @RequestParam("user_id") int userId) {
    }

    @Operation(summary = "예약 취소")
    @PatchMapping("/payments/{reservation_id}")
    public void cancel(
        @PathVariable("reservation_id") int reservationId,
        @RequestParam("user_id") int userId) {
    }
}
