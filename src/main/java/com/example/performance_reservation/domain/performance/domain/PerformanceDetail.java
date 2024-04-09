package com.example.performance_reservation.domain.performance.domain;

import com.example.performance_reservation.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PerformanceDetail extends BaseEntity {

    @Id
    @GeneratedValue
    private long id;

    private long performanceId;

    private int availableSeatNums;

    private int remainSeatNums;

    private LocalDateTime startDate;

    public boolean isAvailableState() {
        return remainSeatNums > 0 && LocalDateTime.now().isBefore(this.startDate);
    }

    public void isValid() {
        if (LocalDateTime.now().isAfter(this.startDate) || remainSeatNums < 0) {
            throw new IllegalArgumentException("예약 가능 상태가 아닙니다.");
        }
    }



}
