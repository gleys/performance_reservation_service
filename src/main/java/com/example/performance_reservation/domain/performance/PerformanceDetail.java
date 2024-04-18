package com.example.performance_reservation.domain.performance;

import com.example.performance_reservation.domain.BaseEntity;
import com.example.performance_reservation.domain.performance.exception.InvalidSeatNoException;
import com.example.performance_reservation.domain.performance.exception.PerformanceNotReservableException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PerformanceDetail extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long performanceId;

    private int amountSeats;

    private int remainSeats;

    private LocalDate startDate;

    public void decreaseRemainSeats() {
        this.remainSeats--;
    }

    public void increaseRemainSeats() {
        this.remainSeats++;
    }

    public void isReservable() {
        if (LocalDate.now().isAfter(this.startDate) || remainSeats <= 0) {
            throw PerformanceNotReservableException.Exception;
        }
    }

    public void isValidSeatNo(final int seatNo) {
        if (seatNo <= 0 || seatNo > 50) {
            throw InvalidSeatNoException.Exception;
        }
    }





}
