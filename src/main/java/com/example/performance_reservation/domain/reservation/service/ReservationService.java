package com.example.performance_reservation.domain.reservation.service;

import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.dto.Bill;
import com.example.performance_reservation.domain.reservation.exception.AlreadyReservedException;
import com.example.performance_reservation.domain.reservation.exception.ReservationNotFoundException;
import com.example.performance_reservation.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public List<Reservation> getMyReservations(final long userId) {
        return reservationRepository.getMyReservations(userId);
    }

    public List<Reservation> getReservations(final long performanceDetailId) {
        return reservationRepository.getReservations(performanceDetailId);
    }

    public Reservation reserve(final Bill bill) {
        this.isAlreadyReserve(bill.performanceDetailId(), bill.seatNo());
        Reservation reservation = new Reservation(bill);
        return reservationRepository.save(reservation);
    }

    public void isAlreadyReserve(final long performanceDetailId, final int seatNo) {
        if (reservationRepository.isExist(performanceDetailId, seatNo)) {
            throw AlreadyReservedException.Exception;
        };
    }
    public void pay(final long userId, final long reservationId) {
        Reservation reservation = reservationRepository.getReservation(userId, reservationId)
                                  .orElseThrow(() -> ReservationNotFoundException.Exception);
        reservation.isValid();
        reservation.pay();
        reservationRepository.save(reservation);
    }

    public long cancel(final long userId, final long reservationId) {
        Reservation reservation = reservationRepository.getReservation(userId, reservationId)
                                  .orElseThrow(() -> ReservationNotFoundException.Exception);
        reservation.cancel();
        reservationRepository.save(reservation);
        return reservation.getPerformanceDetailId();
    }

}