package com.example.performance_reservation.infrastructure.waitingqueue;

import com.example.performance_reservation.domain.waitingqueue.WaitingInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InMemoryWaitingQueueTest {

    private InMemoryWaitingQueue waitingQueue;
    private FakeTimeHolder timeHolder;
    @BeforeEach
    void init() {
        this.timeHolder = new FakeTimeHolder();
        this.waitingQueue = new InMemoryWaitingQueue(timeHolder);
    }

    @Test
    void 순차적으로_대기열_진입한다() {
        // given
        HashMap<Integer, UUID> tokenMap = new HashMap<>();
        timeHolder.setTime(LocalDateTime.now().plusMinutes(5));

        // when
        for (int i = 1; i < 2000; i++) {
            UUID token = waitingQueue.waiting(i);
            tokenMap.put(i,token);
        }
        UUID uuid = tokenMap.get(1500);
        WaitingInfo info = waitingQueue.getInfo(uuid);

        // then
        assertThat(info.waitingNum()).isEqualTo(1500);
        assertThat(info.totalNum()).isEqualTo(1500);
    }

    @Test
    void 대기열_진입_순서가_최대_입장인원_이내이면_입장가능하다() throws InterruptedException {
        // given
        HashMap<Integer, UUID> tokenMap = new HashMap<>();
        timeHolder.setTime(LocalDateTime.now().plusMinutes(5));

        // when
        for (int i = 1; i < 2000; i++) {
            UUID token = waitingQueue.waiting(i);
            tokenMap.put(i,token);
        }
        UUID token = tokenMap.get(300);
        this.waitingQueue.update();
        WaitingInfo waitingInfo = waitingQueue.getInfo(token);

        // then
        assertThat(waitingInfo.isAvailable()).isEqualTo(true);
    }

    @Test
    void 대기열에_없는_토큰_조회시_예외를_던진다() {
        // given
        HashMap<Integer, UUID> tokenMap = new HashMap<>();
        LocalDateTime expiredTime = LocalDateTime.now().minusMinutes(10);
        timeHolder.setTime(expiredTime);

        // when & then
        for (int i = 1; i < 300; i++) {
            UUID token = waitingQueue.waiting(i);
            tokenMap.put(i, token);
        }
        timeHolder.setTime(LocalDateTime.now().plusMinutes(5));
        for (int i = 300; i < 2000; i++) {
            UUID token = waitingQueue.waiting(i);
            tokenMap.put(i,token);
        }

        UUID unknownToken = UUID.randomUUID();
        assertThatThrownBy(() -> waitingQueue.getInfo(unknownToken))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 토큰입니다.");
    }

    @Test
    void 대기열_정보를_얻을_때_토큰이_만료되면_대기열에서_삭제한다() {
        HashMap<Integer, UUID> tokenMap = new HashMap<>();
        LocalDateTime expiredTime = LocalDateTime.now().minusMinutes(10);
        timeHolder.setTime(expiredTime);

        // when
        for (int i = 1; i <= 300; i++) {
            UUID token = waitingQueue.waiting(i);
            tokenMap.put(i, token);
        }
        timeHolder.setTime(LocalDateTime.now().plusMinutes(5));
        for (int i = 300; i < 2000; i++) {
            UUID token = waitingQueue.waiting(i);
            tokenMap.put(i,token);
        }
        UUID expiredToken = tokenMap.get(299);
        assertThatThrownBy(() -> waitingQueue.getInfo(expiredToken));

        // then
        assertThat(waitingQueue.getTotalWaitNum()).isEqualTo(1999);
    }

    @Test
    void 대기열_정보를_얻을_때_토큰이_만료되면_예외를_던진다() {
        HashMap<Integer, UUID> tokenMap = new HashMap<>();
        LocalDateTime expiredTime = LocalDateTime.now().minusMinutes(10);
        timeHolder.setTime(expiredTime);

        // when & then
        for (int i = 1; i <= 300; i++) {
            UUID token = waitingQueue.waiting(i);
            tokenMap.put(i, token);
        }
        timeHolder.setTime(LocalDateTime.now().plusMinutes(5));
        for (int i = 300; i < 2000; i++) {
            UUID token = waitingQueue.waiting(i);
            tokenMap.put(i,token);
        }
        UUID expiredToken = tokenMap.get(299);
        assertThatThrownBy(() -> waitingQueue.getInfo(expiredToken))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("만료된 토큰입니다.");
    }

    @Test
    void 대기열_정보_갱신_시_만료된_토큰을_삭제한다() throws InterruptedException {
        // given
        HashMap<Integer, UUID> tokenMap = new HashMap<>();
        timeHolder.setTime(LocalDateTime.now().minusMinutes(5));

        // when
        for (int i = 1; i <= 500; i++) {
            UUID token = waitingQueue.waiting(i);
            tokenMap.put(i,token);
        }

        timeHolder.setTime(LocalDateTime.now().plusMinutes(5));
        for (int i = 501; i <= 1200; i++) {
            UUID token = waitingQueue.waiting(i);
            tokenMap.put(i,token);
        }

        waitingQueue.update();
        UUID expiredToken = tokenMap.get(300);
        UUID availableToken  = tokenMap.get(600);
        WaitingInfo waitingInfo = waitingQueue.getInfo(availableToken);

        // then
        assertThatThrownBy(() -> waitingQueue.getInfo(expiredToken))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("존재하지 않는 토큰입니다.");
        assertThat(waitingInfo.isAvailable()).isEqualTo(true);
        assertThat(waitingQueue.getTotalWaitNum()).isEqualTo(200);
        assertThat(waitingQueue.getTotalEnterNum()).isEqualTo(500);
    }

    @Test
    void 동시요청_수_만큼_대기열의_크기가_증가한다() throws InterruptedException {
        // given
        int total = 3200;
        ExecutorService executorService = Executors.newFixedThreadPool(total);
        CountDownLatch latch = new CountDownLatch(total);
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

        // then
        assertThat(waitingQueue.getTotalWaitNum()).isEqualTo(total);
    }

    @Test
    void 동시요청_후_갱신시_입장가능_토큰은_대기열에서_제거() throws InterruptedException {
        // given
        int total = 1600;
        ExecutorService executorService = Executors.newFixedThreadPool(total);
        CountDownLatch latch = new CountDownLatch(total);
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
        waitingQueue.update();

        // then
        assertThat(waitingQueue.getTotalWaitNum()).isEqualTo(600);
        assertThat(waitingQueue.getTotalEnterNum()).isEqualTo(1000);
        assertThat(waitingQueue.getTotalSequentialTokensSize()).isEqualTo(600);
    }



}