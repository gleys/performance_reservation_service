package com.example.performance_reservation.domain.queue;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
    uniqueConstraints = {@UniqueConstraint(columnNames = {"userId", "performanceId"})},
    indexes = @Index(columnList = "performance_id, expired_at")
)
@Entity
public class Queue {

    @Id
    @GeneratedValue
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