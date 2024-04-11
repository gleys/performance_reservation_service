package com.example.performance_reservation.domain.reservation.repository;

import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.ReservationHistory;

import java.util.List;

public interface ReservationRepository {
    List<ReservationHistory> getReservationHistories(final long userId);
    ReservationHistory getReservationHistory(final long userId, final long historyId);
    Reservation getReservation(final long historyId);
    ReservationHistory save(final ReservationHistory entity);
    Reservation save(final Reservation entity);

}
