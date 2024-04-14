package com.example.performance_reservation.infrastructure.waitingqueue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@ActiveProfiles("test")
@SpringBootTest
public class InMemoryWaitingQueueConcurrencyTest {

    @Autowired
    private InMemoryWaitingQueue waitingQueue;

    @Autowired
    private FakeTimeHolder timeHolder;


    @Test
    public void 스케줄_시간마다_대기열이_자동_갱신된다() throws InterruptedException {
        // given
        int total = 3200;
        CountDownLatch latch = new CountDownLatch(total);
        ExecutorService executorService = Executors.newFixedThreadPool(total);
        timeHolder.setTime(LocalDateTime.now().plusMinutes(5));

        // when
        for (int i = 1; i <= total; i++) {
            int finalI = i;
            executorService.submit(() -> {
                waitingQueue.waiting(finalI);
                latch.countDown();
            });
        }
        latch.await();
        waitingQueue.getTotalSequentialTokensSize();

        // then
        await().atMost(3500, TimeUnit.MILLISECONDS)
                .untilAsserted(() -> {
                     assertThat(waitingQueue.getTotalWaitNum()).isEqualTo(200);
        });

    }

}
