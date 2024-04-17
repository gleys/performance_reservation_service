package com.example.performance_reservation.domain.reservation;

import com.example.performance_reservation.domain.BaseEntity;
import com.example.performance_reservation.domain.reservation.dto.ReservationHistoryDto;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class ReservationHistory extends BaseEntity {
    @Id
    @Getter
    @GeneratedValue
    private long id;

    @Getter
    private long userId;

    private String title;

    private String performer;

    @Column(unique = true)
    private long seatInfoId;

    private LocalDateTime startDate;

    private int price;

    @Getter
    @Enumerated(EnumType.STRING)
    private HistoryState state;

    @Getter
    private LocalDateTime expiredAt;

    public ReservationHistory(ReservationHistoryDto dto) {
        this.userId = dto.userId();
        this.title = dto.title();
        this.seatInfoId = dto.seatInfoId();
        this.performer = dto.performer();
        this.startDate = dto.startDate();
        this.price = dto.price();
        this.state = HistoryState.PENDING;
        this.expiredAt = dto.now().plusMinutes(5);
    }

    public void expire() {
        if (LocalDateTime.now().isAfter(this.expiredAt))
            this.state = HistoryState.EXPIRED;
    }

    public void complete() {
        if (LocalDateTime.now().isBefore(this.expiredAt))
            this.state = HistoryState.PURCHASED;
    }

    public void cancel() {
        this.state = LocalDateTime.now().isBefore(expiredAt)
                    ? HistoryState.PENDING
                    : HistoryState.EXPIRED;
    }

    public void isValid() {
        if (LocalDateTime.now().isAfter(this.expiredAt)) throw new IllegalArgumentException("임시 예약 시간이 만료 되었습니다.");
        if (!(this.state == HistoryState.PENDING)) throw new IllegalArgumentException("임시 예약 상태가 아닙니다.");
    }
}