package com.example.performance_reservation.infrastructure.waitingqueue;

import com.example.performance_reservation.domain.waitingqueue.TimeHolder;
import com.example.performance_reservation.domain.waitingqueue.WaitingQueue;
import com.example.performance_reservation.domain.waitingqueue.WaitingInfo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

@Slf4j
@RequiredArgsConstructor
@Component
public class InMemoryWaitingQueue implements WaitingQueue {
    /**
     * map 에 key - user identifier / value - concurrentLinkedQueue ref
     */

    @Getter
    private final Map<UUID, WaitingInfo> waiting = new ConcurrentHashMap<>();

    @Getter
    private final Queue<UUID> sequentialTokens = new ConcurrentLinkedQueue<>();

    @Getter
    private final Map<UUID, WaitingInfo> entering = new HashMap<>();

    private final int NUMBER_OF_ENTERING = 1000;
    private final TimeHolder timeHolder;

    @Override
    public UUID waiting(final long userId) {
        UUID token = UUID.randomUUID();
        sequentialTokens.add(token);
        int currentNum = sequentialTokens.size();
        LocalDateTime expiredAt = timeHolder.getExpiredTime();
        WaitingInfo waitingInfo = WaitingInfo.init(userId, currentNum, expiredAt);
        waiting.put(token, waitingInfo);
        waiting.replace(token, waitingInfo.update(waitingInfo.waitingNum(), sequentialTokens.size()));
        return token;
    }

    // passive update
    @Override
    public WaitingInfo getInfo(final UUID token) {
        log.info("scheduler start");
        boolean isEntering = entering.containsKey(token);
        boolean isWaiting = waiting.containsKey(token);
        if (!isWaiting && !isEntering) throw new IllegalArgumentException("존재하지 않는 토큰입니다.");
        WaitingInfo waitingInfo = isEntering
                                  ? entering.get(token)
                                  : waiting.get(token);
        if (waitingInfo.isExpire()) {
            this.remove(token);
            throw new IllegalArgumentException("만료된 토큰입니다.");
        }
        return waitingInfo;
    }

    @Override
    public void remove(final UUID token) {
        WaitingInfo waitingInfo = entering.containsKey(token)
                                  ? entering.remove(token)
                                  : waiting.remove(token);
        sequentialTokens.remove(token);
    }

    // active update

    public void update() {
        List<UUID> tokens = sequentialTokens.stream().toList();
        moveToEntering(tokens);
        updateWaiting();
    }

    public void updateWaiting() {
        int totalWaitingNum = waiting.size();
        waiting.entrySet().stream()
                .map(entry -> {
                    WaitingInfo waitingInfo = entry.getValue();
                    return new AbstractMap.SimpleEntry<>(entry.getKey(),
                            waitingInfo.update(waitingInfo.waitingNum() - NUMBER_OF_ENTERING, totalWaitingNum));
                })
                .forEach(entry -> waiting.replace(entry.getKey(), entry.getValue()));
    }

    public void moveToEntering(final List<UUID> tokens) {
        tokens.stream()
            .limit(NUMBER_OF_ENTERING)
            .map(this::getOutWaiting)
            .filter(entry -> !isExpire(entry))
            .forEach(this::entering);
    }

    public int getTotalWaitNum() {
        return this.waiting.size();
    }

    public int getTotalEnterNum() {
        return this.entering.size();
    }

    public int getTotalSequentialTokensSize() {
        log.info("{}", this);
        return this.sequentialTokens.size();
    }


    private boolean isExpire(final AbstractMap.SimpleEntry<UUID, WaitingInfo> entry) {
        return entry.getValue().isExpire();
    }

    private void entering(final AbstractMap.SimpleEntry<UUID, WaitingInfo> entry) {
        WaitingInfo waitingInfo = entry.getValue();
        entering.put(entry.getKey(), waitingInfo.enter());
    }

    private AbstractMap.SimpleEntry<UUID, WaitingInfo> getOutWaiting(final UUID token) {
        WaitingInfo waitingInfo = this.waiting.get(token);
        sequentialTokens.remove(token);
        this.remove(token);
        return new AbstractMap.SimpleEntry<>(token, waitingInfo);
    }

}