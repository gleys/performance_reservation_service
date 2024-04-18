package com.example.performance_reservation.infrastructure.reservation;

import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.ReservationState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JpaReservationRepository extends JpaRepository<Reservation, Long> {
    Reservation save(final Reservation entity);
    List<Reservation> findByUserId(final long userId);
    Optional<Reservation> findByUserIdAndId(final long userId, final long id);
    boolean existsByPerformanceDetailIdAndSeatNo(final long performanceDetailId, final int seatNo);
    List<Reservation> findByPerformanceDetailId(final long performanceDetailId);

    @Modifying
    @Query(value = "delete from reservation where state = :state and expired_at < :now", nativeQuery = true)
    void delteByExpiredAtBeforeAndState(@Param("state") String state,
                                       @Param("now")LocalDateTime now);



}
