package com.example.performance_reservation.infrastructure.reservation;

import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.repository.ReservationRepository;

import java.util.*;

public class FakeReservationRepository implements ReservationRepository {

    private List<Reservation> reservations = new ArrayList<>();


    @Override
    public List<Reservation> getMyReservations(final long userId) {
        return reservations;
    }

    @Override
    public Optional<Reservation> getReservation(final long userId, final long reservationId) {
        return reservations.stream()
               .filter(history -> ((history.getUserId() == userId) && (history.getId() == reservationId)))
               .findFirst();

    }

    @Override
    public boolean isExist(final long performanceDetailId, final int seatNo) {
        return false;
    }



    public void historyBulkInsert(List<Reservation> histories) {
        this.reservations.addAll(histories);
    }
    @Override
    public Reservation save(final Reservation entity) {
        reservations.add(entity);
        return entity;
    }

    @Override
    public List<Reservation> getReservations(final long performanceDetailId) {
        return null;
    }

    @Override
    public void removeExpiredReservations() {
    }

}
