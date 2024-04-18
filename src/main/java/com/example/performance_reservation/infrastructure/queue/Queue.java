package com.example.performance_reservation.infrastructure.queue;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class Queue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long userId;

    private boolean isValid;

    private long performanceId;

    private long expiredAt;

    public Queue(final long userId, final long performanceId, long expiredAt) {
        this.userId = userId;
        this.performanceId = performanceId;
        this.isValid = false;
        this.expiredAt = expiredAt;
    }
}