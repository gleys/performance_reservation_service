package com.example.performance_reservation.domain.reservation.service;

import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.ReservationState;
import com.example.performance_reservation.domain.reservation.dto.Bill;
import com.example.performance_reservation.domain.reservation.exception.ReservationNotFoundException;
import com.example.performance_reservation.domain.reservation.exception.ReservationTimeExpiredException;
import com.example.performance_reservation.infrastructure.reservation.FakeReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ReservationServiceTest {

    private ReservationService reservationService;
    private FakeReservationRepository repository;
    @BeforeEach
    void init() {
        repository = new FakeReservationRepository();
        Reservation history1 = new Reservation(1, 1, "test1", "test1", 1, 1, LocalDate.now().plusDays(5), 50000, ReservationState.PENDING, LocalDateTime.now().plusMinutes(5));
        Reservation history2 = new Reservation(2, 1, "test2", "test2", 2, 2, LocalDate.now().plusDays(3), 50000, ReservationState.EXPIRED, LocalDateTime.now().minusMinutes(5));
        Reservation history3 = new Reservation(3, 1, "test3", "test3", 3, 3, LocalDate.now().plusDays(6), 50000, ReservationState.EXPIRED, LocalDateTime.now().minusMinutes(5));
        Reservation history4 = new Reservation(4, 1, "test4", "test4", 4, 4, LocalDate.now().plusDays(1), 50000, ReservationState.PENDING, LocalDateTime.now().plusMinutes(5));
        Reservation history5 = new Reservation(5, 1, "test5", "test5", 5, 5, LocalDate.now().plusDays(2), 50000, ReservationState.PENDING, LocalDateTime.now().plusMinutes(5));
        repository.historyBulkInsert(List.of(history1, history2, history3, history4, history5));
        this.reservationService = new ReservationService(repository);
    }

    @Test
    void 해당_유저의_예약_히스토리를_가져온다() {
        // given
        long userId = 1;

        //when
        List<Reservation> reservations = reservationService.getMyReservations(userId);

        // then
        reservations.forEach(history -> assertThat(history.getUserId()).isEqualTo(userId));
    }

    @Test
    void 임시_예약을_생성한다() {
        // given
        LocalDateTime now = LocalDateTime.now();
        Bill bill = new Bill(1, 2, 1,"new", "new", LocalDate.now().plusDays(2), now, 50000);

        // when
        Reservation history = reservationService.reserve(bill);

        // then
        assertThat(history.getExpiredAt()).isEqualTo(now.plusMinutes(5));
    }

    @Test
    void 임시_예약_만료후_결제시_예외를_던진다() {
        // given
        long userId = 1;
        long historyId = 2;

        // when & then
        assertThatThrownBy(() -> this.reservationService.pay(userId, historyId))
                .isInstanceOf(ReservationTimeExpiredException.class);
    }

    @Test
    void 임시_예약_내에_결제시_성공한다() {
        // given
        long userId = 1;
        long reservationId = 1;

        // when
        this.reservationService.pay(userId, reservationId);
        Reservation reservation = repository.getReservation(userId, reservationId).get();

        // then
        assertThat(reservation.getState()).isEqualTo(ReservationState.PURCHASED);
        assertThat(reservation.getId()).isEqualTo(reservationId);
    }

}