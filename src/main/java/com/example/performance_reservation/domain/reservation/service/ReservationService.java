package com.example.performance_reservation.domain.reservation.service;

import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.ReservationHistory;
import com.example.performance_reservation.domain.reservation.dto.ReservationHistoryDto;
import com.example.performance_reservation.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ReservationService {
    private final ReservationRepository reservationRepository;

    public List<ReservationHistory> getHistories(final long userId) {
        return reservationRepository.getReservationHistories(userId);
    }

    public ReservationHistory reserve(ReservationHistoryDto dto) {
        ReservationHistory history = new ReservationHistory(dto);
        return reservationRepository.save(history);
    }

    public void pay(final long userId, final long historyId) {
        ReservationHistory history = reservationRepository.getReservationHistory(userId, historyId);
        history.isValid();
        Reservation reservation = new Reservation(userId, historyId);
        history.complete();
        reservation.pay();
        reservationRepository.save(reservation);
    }

    public void cancel(final long userId, final long historyId) {
        ReservationHistory history = reservationRepository.getReservationHistory(userId, historyId);
        Reservation reservation = reservationRepository.getReservation(historyId);
        history.cancel();
        reservation.cancel();
    }

}