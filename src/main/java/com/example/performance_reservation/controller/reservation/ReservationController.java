package com.example.performance_reservation.controller.reservation;

import com.example.performance_reservation.application.reservation.PaymentFacade;
import com.example.performance_reservation.application.reservation.ReservationFacade;
import com.example.performance_reservation.application.reservation.ReservationReadService;
import com.example.performance_reservation.controller.reservation.response.ReservationInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Tag(name = "예약")
@RequiredArgsConstructor
@RequestMapping("/reservations")
@RestController
public class ReservationController {

    private final ReservationFacade reservationFacade;

    private final ReservationReadService readReservationService;

    private final PaymentFacade paymentFacade;

    @Operation(summary = "예약 내역 조회")
    @GetMapping
    public List<ReservationInfoResponse> getReservation(@RequestParam("user_id") final long userId) {
        return readReservationService.getReservation(userId);
    }

    @Operation(summary = "좌석 임시 예약(예약 후 5분간 유효함)")
    @PostMapping("/{seat_id}")
    public void reserve(
            @RequestHeader final UUID token,
            @RequestParam("performance_detail") final long id,
            @RequestParam("seat_no") final int seatNo) {
        reservationFacade.makeTemporaryReservation(token, id, seatNo);
    }

    @Operation(summary = "결제(임시 예약 상태에서만 가능함)")
    @PostMapping("/payments/{reservation_id}")
    public void pay(
        @PathVariable("reservation_id") int reservationId,
        @RequestParam("user_id") int userId) {
        paymentFacade.pay(reservationId, userId);
    }

    @Operation(summary = "예약 취소")
    @PatchMapping("/payments/{reservation_id}")
    public void cancel(
        @PathVariable("reservation_id") int reservationId,
        @RequestParam("user_id") int userId) {
        paymentFacade.cancel(reservationId, userId);
    }
}
