package com.example.performance_reservation.domain.wallet;

import com.example.performance_reservation.domain.BaseEntity;
import com.example.performance_reservation.domain.user.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Wallet extends BaseEntity {

    @Id
    @GeneratedValue
    private long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    private long amount;

}