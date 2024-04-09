package com.example.performance_reservation.domain.reservation.domain;

import com.example.performance_reservation.domain.BaseEntity;
import com.example.performance_reservation.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "history_id"})})
@Entity
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue
    private long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "history_id")
    private ReservationHistory history;

    @Enumerated(EnumType.STRING)
    private ReservationState state;


}