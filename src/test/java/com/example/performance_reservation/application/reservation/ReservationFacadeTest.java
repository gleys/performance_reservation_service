package com.example.performance_reservation.application.reservation;

import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.repository.ReservationRepository;
import com.example.performance_reservation.domain.waitingqueue.WaitingQueue;
import com.example.performance_reservation.infrastructure.performance.PerformanceRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Transactional
@SpringBootTest
class ReservationFacadeTest {

    @Autowired
    private ReservationFacade reservationFacade;

    @Autowired
    private PerformanceRepositoryImpl performanceRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private WaitingQueue waitingQueue;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void init() {
        Performance performance = new Performance(1, "test", "test", 10000);
        PerformanceDetail performanceDetail = new PerformanceDetail(1, 1, 50, 50, LocalDate.now().plusDays(1));
        performanceRepository.save(performance);
        performanceRepository.save(performanceDetail);
    }

    @AfterEach
    void exit() {
        String query = "delete from reservation";
        jdbcTemplate.update(query);

    }

    @Test
    void 사용자가_동시에_동일_좌석_예매_요청시_최초_요청만_성공한다() throws InterruptedException {
        // given
        int total = 1000;
        CountDownLatch countDownLatch = new CountDownLatch(total);
        ExecutorService executorService = Executors.newFixedThreadPool(total);
        Random random = new Random(3);

        // when
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();

        UUID token = waitingQueue.waiting(1);
        for (int i = 1; i <= total; i++) {
            executorService.submit(() -> {
                try {
                    int num = random.nextInt(50) + 1;
                    reservationFacade.makeTemporaryReservation(token, 1, num);
                    successCount.getAndIncrement();
                } catch (Exception e) {
                    failCount.getAndIncrement();
                } finally {
                    countDownLatch.countDown(); // 작업 완료 신호 발송
                }
            });
        }


        // then
        countDownLatch.await();
        List<Reservation> reservations = reservationRepository.getMyReservations(1);
        List<Integer> seatList = reservations.stream().map(Reservation::getSeatNo).sorted().toList();
        List<Integer> seatSetToList  = reservations.stream().map(Reservation::getSeatNo).collect(Collectors.toSet()).stream().sorted().toList();

        PerformanceDetail performanceDetail = performanceRepository.getPerformanceDetailWithLock(1).get();
        Assertions.assertThat(successCount.get() + failCount.get()).isEqualTo(total);
        Assertions.assertThat(seatList).isEqualTo(seatSetToList);
        Assertions.assertThat(performanceDetail.getAmountSeats()).isEqualTo(performanceDetail.getRemainSeats() + reservations.size());
    }

}