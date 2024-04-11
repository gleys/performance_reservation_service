package com.example.performance_reservation.domain.reservation;

import com.example.performance_reservation.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "history_id"})})
@Entity
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue
    private long id;

    private long userId;

    @Getter
    private long historyId;

    @Getter
    @Enumerated(EnumType.STRING)
    private ReservationState state;

    public Reservation(final long userId, final long historyId) {
        this.userId = userId;
        this.historyId = historyId;
    }

    public void cancel() {
        this.state = ReservationState.CANCEL;
    }

    public void pay() {
        this.state = ReservationState.ASSIGN;
    }


}