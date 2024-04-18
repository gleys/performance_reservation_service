package com.example.performance_reservation.domain.waitingqueue.scheduler;

import com.example.performance_reservation.infrastructure.waitingqueue.InMemoryWaitingQueue;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.awaitility.Awaitility.await;

@SpringBootTest
class WaitingQueueSchedulerTest {
    @Autowired
    private InMemoryWaitingQueue waitingQueue;

    @Test
    public void 주기적으로_대기열이_갱신된다() throws InterruptedException {
        // given
        int total = 3200;
        CountDownLatch latch = new CountDownLatch(total);
        ExecutorService executorService = Executors.newFixedThreadPool(total);

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