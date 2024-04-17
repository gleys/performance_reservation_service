package com.example.performance_reservation.application.reservation;

import com.example.performance_reservation.domain.performance.OccupancyState;
import com.example.performance_reservation.domain.performance.Performance;
import com.example.performance_reservation.domain.performance.PerformanceDetail;
import com.example.performance_reservation.domain.performance.Seat;
import com.example.performance_reservation.domain.reservation.Reservation;
import com.example.performance_reservation.domain.reservation.repository.ReservationRepository;
import com.example.performance_reservation.domain.waitingqueue.WaitingQueue;
import com.example.performance_reservation.infrastructure.performance.PerformanceRepositoryImpl;
import com.example.performance_reservation.infrastructure.performance.SeatRepositoryImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//@Transactional
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
    @BeforeEach
    void init() {
        Performance performance = new Performance(1, "test", "test", 10000);
        PerformanceDetail performanceDetail = new PerformanceDetail(1, 1, 50, 50, LocalDateTime.now().plusDays(1));
        performanceRepository.save(performance);
        performanceRepository.save(performanceDetail);
    }

    @Test
    void 여러_유저가_동시에_예약한다() throws InterruptedException {
        // given
        int total = 50;
        CountDownLatch endLatch = new CountDownLatch(total);
        ExecutorService executorService = Executors.newFixedThreadPool(total);
        Random random = new Random(3);

        // when
        int successCount = 0;
        int failCount = 0;
        UUID token = waitingQueue.waiting(1);
        for (int i = 1; i <= total; i++) {
            executorService.submit(() -> {
                try {
                    int num = random.nextInt(50) + 1;
                    System.out.println(num);
                    reservationFacade.makeTemporaryReservation(token, 1, num);
                } finally {
                    endLatch.countDown(); // 작업 완료 신호 발송
                }
            });
        }


        // then
        endLatch.await();
        List<Reservation> reservations = reservationRepository.getReservations(1);
        PerformanceDetail performanceDetail = performanceRepository.getPerformanceDetail(1).get();
        Assertions.assertThat(performanceDetail.getAvailableSeatNums()).isEqualTo(performanceDetail.getRemainSeatNums() + reservations.size());

    }

}