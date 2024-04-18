package com.example.performance_reservation.application.reservation;

import com.example.performance_reservation.domain.performance.service.PerformanceService;
import com.example.performance_reservation.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class PaymentFacade {
    private final ReservationService reservationService;
    private final PerformanceService performanceService;

    public void pay(int reservationId, int userId) {
        reservationService.pay(userId, reservationId);
    }

    public void cancel(int reservationId, int userId) {
        long performanceDetailId = reservationService.cancel(userId, reservationId);
        performanceService.increaseRemainSeats(performanceDetailId);
    }
}
