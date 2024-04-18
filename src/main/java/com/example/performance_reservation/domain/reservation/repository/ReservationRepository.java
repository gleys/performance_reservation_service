package com.example.performance_reservation.domain.reservation.repository;

import com.example.performance_reservation.domain.reservation.Reservation;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {
    List<Reservation> getMyReservations(final long userId);
    Optional<Reservation> getReservation(final long userId, final long reservationId);
    boolean isExist(final long performanceDetailId, final int seatNo);
    Reservation save(final Reservation entity);
    List<Reservation> getReservations(final long performanceDetailId);
    void removeExpiredReservations();

}
