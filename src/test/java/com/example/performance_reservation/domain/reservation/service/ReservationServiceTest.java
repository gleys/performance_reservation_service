package com.example.performance_reservation.domain.reservation.service;

import com.example.performance_reservation.domain.reservation.HistoryState;
import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.ReservationHistory;
import com.example.performance_reservation.domain.reservation.ReservationState;
import com.example.performance_reservation.domain.reservation.dto.ReservationHistoryDto;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        ReservationHistory history1 = new ReservationHistory(1, 1, "test1", "test1", 1, LocalDateTime.now().plusDays(5), 50000, HistoryState.PENDING, LocalDateTime.now().plusMinutes(5));
        ReservationHistory history2 = new ReservationHistory(2, 1, "test2", "test2", 2, LocalDateTime.now().plusDays(3), 50000, HistoryState.EXPIRED, LocalDateTime.now().minusMinutes(5));
        ReservationHistory history3 = new ReservationHistory(3, 1, "test3", "test3", 3, LocalDateTime.now().plusDays(6), 50000, HistoryState.EXPIRED, LocalDateTime.now().minusMinutes(5));
        ReservationHistory history4 = new ReservationHistory(4, 1, "test4", "test4", 4, LocalDateTime.now().plusDays(1), 50000, HistoryState.PENDING, LocalDateTime.now().plusMinutes(5));
        ReservationHistory history5 = new ReservationHistory(5, 1, "test5", "test5", 5, LocalDateTime.now().plusDays(2), 50000, HistoryState.PENDING, LocalDateTime.now().plusMinutes(5));
        repository.historyBulkInsert(List.of(history1, history2, history3, history4, history5));
        this.reservationService = new ReservationService(repository);
    }

    @Test
    void 해당_유저의_예약_히스토리를_가져온다() {
        // given
        long userId = 1;

        //when
        List<ReservationHistory> histories = reservationService.getHistories(userId);

        // then
        histories.forEach(history -> assertThat(history.getUserId()).isEqualTo(userId));
    }

    @Test
    void 임시_예약을_생성한다() {
        // given
        LocalDateTime now = LocalDateTime.now();
        ReservationHistoryDto historyDto = new ReservationHistoryDto(1, 2, "new", "new", LocalDateTime.now().plusDays(2), now, 50000);

        // when
        ReservationHistory history = reservationService.reserve(historyDto);
        ReservationHistory findHistory = repository.getReservationHistory(1, history.getId());

        // then
        assertThat(history).isEqualTo(findHistory);
        assertThat(history.getExpiredAt()).isEqualTo(now.plusMinutes(5));
    }

    @Test
    void 임시_예약_만료후_결제시_예외를_던진다() {
        // given
        long userId = 1;
        long historyId = 2;

        // when & then
        assertThatThrownBy(() -> this.reservationService.pay(userId, historyId))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 임시_예약_내에_결제시_성공한다() {
        // given
        long userId = 1;
        long historyId = 1;

        // when
        this.reservationService.pay(userId, historyId);
        Reservation reservation = repository.getReservation(historyId);
        ReservationHistory history = repository.getReservationHistory(userId, historyId);

        // then
        assertThat(history.getState()).isEqualTo(HistoryState.PURCHASED);
        assertThat(reservation.getHistoryId()).isEqualTo(historyId);
        assertThat(reservation.getState()).isEqualTo(ReservationState.ASSIGN);
    }

}