package com.example.performance_reservation.infrastructure.reservation;

import com.example.performance_reservation.domain.reservation.ReservationHistory;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface JpaReservationHistoryRepository extends Repository<ReservationHistory, Long> {
    List<ReservationHistory> findByUserId(final long userId);
    Optional<ReservationHistory> findByUserIdAndId(final long userId, final long id);
    ReservationHistory save(final ReservationHistory entity);
}
