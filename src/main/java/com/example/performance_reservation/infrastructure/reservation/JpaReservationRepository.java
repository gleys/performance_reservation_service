package com.example.performance_reservation.infrastructure.reservation;

import com.example.performance_reservation.domain.reservation.Reservation;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface JpaReservationRepository extends Repository<Reservation, Long> {
    Reservation save(final Reservation entity);
    Optional<Reservation> findByHistoryId(final long historyId);
}
