package com.example.performance_reservation.domain.performance;

import com.example.performance_reservation.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PerformanceDetail extends BaseEntity {

    @Id
    @GeneratedValue
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    private int availableSeatNums;

    private int remainSeatNums;

    private LocalDateTime startedDate;

    public boolean isAvailableState() {
        return remainSeatNums > 0 && LocalDateTime.now().isBefore(this.startedDate);
    }

    public void isValid() {
        if (LocalDateTime.now().isAfter(this.startedDate) || remainSeatNums < 0) {
            throw new IllegalArgumentException("예약 가능 상태가 아닙니다.");
        }
    }



}
