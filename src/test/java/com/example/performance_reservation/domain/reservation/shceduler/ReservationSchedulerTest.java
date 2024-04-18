package com.example.performance_reservation.domain.reservation.shceduler;

import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.ReservationState;
import com.example.performance_reservation.domain.reservation.repository.ReservationRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
class ReservationSchedulerTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    public void init() {
        ArrayList<Reservation> reservations = new ArrayList<>();

        for (int i = 1; i < 10; i++) {
            // TODO: save 대신 bulk insert 변경
            Reservation reservation = new Reservation(i, 1, "test", "test", i, i,
                    LocalDate.now().plusDays(3), 10000, ReservationState.PENDING, LocalDateTime.now().minusDays(3));
            reservations.add(reservation);
        }

        for (int i = 10; i < 20; i++) {
            Reservation reservation = new Reservation(i, 1, "test", "test", i, i,
                    LocalDate.now().plusDays(3), 10000, ReservationState.PURCHASED, LocalDateTime.now().minusDays(3));
            reservations.add(reservation);
        }
        String query = "insert into reservation (created_at, expired_at, performance_detail_id, performer, price, seat_no, start_date, state, title, updated_at, user_id, id) " +
                        "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.batchUpdate(query, reservations, reservations.size(), (ps, reservation) -> {
            ps.setDate(1, Date.valueOf(LocalDateTime.now().toLocalDate()));
            ps.setDate(2, Date.valueOf(reservation.getExpiredAt().toLocalDate()));
            ps.setLong(3, reservation.getPerformanceDetailId());
            ps.setString(4, reservation.getPerformer());
            ps.setInt(5, reservation.getPrice());
            ps.setInt(6, reservation.getSeatNo());
            ps.setDate(7, Date.valueOf(reservation.getStartDate()));
            ps.setString(8, reservation.getState().name());
            ps.setString(9, reservation.getTitle());
            ps.setDate(10, Date.valueOf(LocalDateTime.now().toLocalDate()));
            ps.setLong(11, reservation.getUserId());
            ps.setLong(12, reservation.getId());
        });
    }

    @AfterEach
    public void exit() {
        String query = "delete from reservation";
        jdbcTemplate.update(query);
    }
    @Test
    public void 주기적으로_만료된_예약을_제거한다() throws InterruptedException {
        // given & when & then
        await().atMost(12000, TimeUnit.MILLISECONDS)
        .untilAsserted(() -> {
            assertThat(reservationRepository.getMyReservations(1).size()).isEqualTo(10);
        });
    }

}