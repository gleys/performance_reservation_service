package com.example.performance_reservation.domain.reservation.service;

import com.example.performance_reservation.domain.reservation.HistoryState;
import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.ReservationHistory;
import com.example.performance_reservation.domain.reservation.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.*;

public class FakeReservationRepository implements ReservationRepository {

    private List<Reservation> reservations = new ArrayList<>();
    private List<ReservationHistory> histories = new ArrayList<>();


    @Override
    public List<ReservationHistory> getReservationHistories(final long userId) {
        return histories.stream()
               .filter(history -> history.getUserId() == userId)
               .toList();
    }

    @Override
    public ReservationHistory getReservationHistory(final long userId, final long historyId) {
        return histories.stream()
               .filter(history -> ((history.getUserId() == userId) && (history.getId() == historyId)))
               .findFirst().orElse(null);
    }

    @Override
    public Reservation getReservation(final long historyId) {
        return reservations.stream()
               .filter(reservation -> reservation.getHistoryId() == historyId)
               .findFirst().orElse(null);

    }

    public void historyBulkInsert(List<ReservationHistory> histories) {
        this.histories.addAll(histories);
    }
    @Override
    public ReservationHistory save(final ReservationHistory entity) {
        histories.add(entity);
        return entity;
    }

    @Override
    public Reservation save(final Reservation entity) {
        reservations.add(entity);
        return null;
    }
}
