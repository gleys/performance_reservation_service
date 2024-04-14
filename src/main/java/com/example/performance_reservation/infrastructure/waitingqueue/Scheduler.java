package com.example.performance_reservation.infrastructure.waitingqueue;

import com.example.performance_reservation.domain.waitingqueue.WaitingInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.AbstractMap;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@Component
public class Scheduler {

    private final InMemoryWaitingQueue waitingQueue;

    @Scheduled(fixedDelay = 1000)
    public void update() throws InterruptedException {
        List<UUID> tokens = waitingQueue.getSequentialTokens().stream().toList();
        moveToEntering(tokens);
        updateWaiting();
    }

    public void updateWaiting() {
        int totalWaitingNum = waitingQueue.getWaiting().size();
        waitingQueue.getWaiting().entrySet().stream()
                .map(entry -> {
                    WaitingInfo waitingInfo = entry.getValue();
                    return new AbstractMap.SimpleEntry<>(entry.getKey(),
                            waitingInfo.update(waitingInfo.waitingNum() - 1000, totalWaitingNum));
                })
                .forEach(entry -> waitingQueue.getWaiting().replace(entry.getKey(), entry.getValue()));
    }

    public void moveToEntering(final List<UUID> tokens) {
        tokens.stream()
                .limit(1000)
                .map(this::getOutWaiting)
                .filter(entry -> !isExpire(entry))
                .forEach(this::entering);
    }

    private AbstractMap.SimpleEntry<UUID, WaitingInfo> getOutWaiting(final UUID token) {
        WaitingInfo waitingInfo = waitingQueue.getWaiting().get(token);
        waitingQueue.getSequentialTokens().remove(token);
        waitingQueue.remove(token);
        return new AbstractMap.SimpleEntry<>(token, waitingInfo);
    }

    private boolean isExpire(final AbstractMap.SimpleEntry<UUID, WaitingInfo> entry) {
        return entry.getValue().isExpire();
    }

    private void entering(final AbstractMap.SimpleEntry<UUID, WaitingInfo> entry) {
        WaitingInfo waitingInfo = entry.getValue();
        waitingQueue.getEntering().put(entry.getKey(), waitingInfo.enter());
    }




}
