package com.example.performance_reservation.domain.waitingqueue.domain;

import java.time.LocalDateTime;

public record WaitingInfo(
    long userId,
    int waitingNum,
    int totalNum,
    boolean isAvailable,
    LocalDateTime expiredAt
) {
    public static WaitingInfo init(long userId, int waitingNum, LocalDateTime expiredAt)  {
        return new WaitingInfo(userId, waitingNum, 0, false, expiredAt);
    }

    public WaitingInfo update(int waitingNum, int totalNum) {
        return new WaitingInfo(
            this.userId,
            waitingNum,
            totalNum,
            this.isAvailable,
            this.expiredAt
        );
    }

    public boolean isExpire() {
        return LocalDateTime.now().isAfter(this.expiredAt());
    }

    public WaitingInfo enter() {
        return new WaitingInfo(
            this.userId,
            0,
                0,
                true,
                this.expiredAt
        );
    }

}