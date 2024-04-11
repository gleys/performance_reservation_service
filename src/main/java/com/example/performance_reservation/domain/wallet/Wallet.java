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

    private long userId;

    private long amount;

}