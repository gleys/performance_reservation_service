package com.example.performance_reservation.domain.reservation;

import com.example.performance_reservation.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ReservationHistory extends BaseEntity {
    @Id
    @GeneratedValue
    private long id;

    private long userId;

    private String title;

    private String performer;

    @Column(unique = true)
    private long seatInfoId;

    private LocalDateTime startDate;

    private int price;

    @Enumerated(EnumType.STRING)
    private HistoryState state;

    private LocalDateTime expiredAt;

    @Builder
    public ReservationHistory(
        final long userId,
        final long seatInfoId,
        final String title,
        final String performer,
        final LocalDateTime startDate,
        final int price
    ) {
        this.userId = userId;
        this.title = title;
        this.seatInfoId = seatInfoId;
        this.performer = performer;
        this.startDate = startDate;
        this.price = price;
        this.state = HistoryState.PENDING;
        this.expiredAt = LocalDateTime.now().plusMinutes(5);
    }

    public void expire() {
        if (LocalDateTime.now().isAfter(this.expiredAt))
            this.state = HistoryState.EXPIRED;
    }

    public void complete() {
        if (LocalDateTime.now().isBefore(this.expiredAt))
            this.state = HistoryState.PURCHASED;
    }

    public void isValid() {
        if (!(LocalDateTime.now().isBefore(this.expiredAt)
                  && this.state == HistoryState.PENDING))
            throw new IllegalArgumentException("임시 배정 시간이 만료 되었습니다.");
    }
}