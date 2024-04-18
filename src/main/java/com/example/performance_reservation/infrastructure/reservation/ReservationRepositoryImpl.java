package com.example.performance_reservation.infrastructure.reservation;

import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.ReservationState;
import com.example.performance_reservation.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class ReservationRepositoryImpl implements ReservationRepository {
    private final JpaReservationRepository reservationRepository;

    @Override
    public List<Reservation> getMyReservations(final long userId) {
        return reservationRepository.findByUserId(userId);
    }

    @Override
    public Optional<Reservation> getReservation(final long userId, final long historyId) {
        return reservationRepository.findByUserIdAndId(userId, historyId);
    }

    @Override
    public boolean isExist(final long performanceDetailId, final int seatNo) {
        return reservationRepository.existsByPerformanceDetailIdAndSeatNo(performanceDetailId, seatNo);
    }

    @Override
    public Reservation save(final Reservation entity) {
        return reservationRepository.save(entity);
    }

    @Override
    public List<Reservation> getReservations(final long performanceDetailId) {
        return reservationRepository.findByPerformanceDetailId(performanceDetailId);
    }

    @Override
    public void removeExpiredReservations() {
        reservationRepository.delteByExpiredAtBeforeAndState(ReservationState.PENDING.name(), LocalDateTime.now());
    }

}
