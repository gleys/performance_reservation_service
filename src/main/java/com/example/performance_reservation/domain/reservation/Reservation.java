package com.example.performance_reservation.domain.reservation;

import com.example.performance_reservation.domain.BaseEntity;
import com.example.performance_reservation.domain.reservation.dto.Bill;
import com.example.performance_reservation.domain.reservation.exception.IllegalReservationStateException;
import com.example.performance_reservation.domain.reservation.exception.ReservationTimeExpiredException;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = { @UniqueConstraint(columnNames = { "performanceDetailId", "seatNo" }) })
@Entity
public class Reservation extends BaseEntity {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Getter
    private long userId;

    private String title;

    private String performer;

    private long performanceDetailId;

    private int seatNo;

    private LocalDate startDate;

    private int price;

    @Enumerated(EnumType.STRING)
    private ReservationState state;

    private LocalDateTime expiredAt;

    public Reservation(Bill bill) {
        this.userId = bill.userId();
        this.title = bill.title();
        this.performanceDetailId = bill.performanceDetailId();
        this.seatNo = bill.seatNo();
        this.performer = bill.performer();
        this.startDate = bill.startDate();
        this.price = bill.price();
        this.state = ReservationState.PENDING;
        this.expiredAt = bill.now().plusMinutes(5);
    }

    public void expire() {
        if (LocalDateTime.now().isAfter(this.expiredAt))
            this.state = ReservationState.EXPIRED;
    }

    public void pay() {
        if (LocalDateTime.now().isBefore(this.expiredAt))
            this.state = ReservationState.PURCHASED;
    }

    public void cancel() {
        this.state = LocalDateTime.now().isBefore(expiredAt)
                    ? ReservationState.PENDING
                    : ReservationState.EXPIRED;
    }

    public void isValid() {
        if (LocalDateTime.now().isAfter(this.expiredAt)) throw ReservationTimeExpiredException.Exception;
        if (!(this.state == ReservationState.PENDING)) throw IllegalReservationStateException.Exception;
    }
}