package com.example.performance_reservation.domain.waitingqueue;

import com.example.performance_reservation.domain.waitingqueue.domain.WaitingInfo;

import java.util.UUID;

public interface WaitingQueue {
    UUID waiting(final long userId);
    WaitingInfo getInfo(final UUID token);
    void remove(final UUID token);
}
