package com.example.performance_reservation.infrastructure.reservation;

import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.ReservationHistory;
import com.example.performance_reservation.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class ReservationRepositoryImpl implements ReservationRepository {
    private final JpaReservationRepository reservationRepository;
    private final JpaReservationHistoryRepository reservationHistoryRepository;

    @Override
    public List<ReservationHistory> getReservationHistories(final long userId) {
        return reservationHistoryRepository.findByUserId(userId);
    }

    @Override
    public ReservationHistory getReservationHistory(final long userId, final long historyId) {
        return reservationHistoryRepository.findByUserIdAndId(userId, historyId)
                       .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 예약번호 입니다."));
    }

    @Override
    public Reservation getReservation(final long historyId) {
        return reservationRepository.findByHistoryId(historyId)
                       .orElseThrow(() -> new IllegalArgumentException("결제 내역이 존재하지 않습니다."));

    }

    @Override
    public ReservationHistory save(final ReservationHistory entity) {
        return reservationHistoryRepository.save(entity);
    }

    @Override
    public Reservation save(final Reservation entity) {
        return reservationRepository.save(entity);
    }
}
