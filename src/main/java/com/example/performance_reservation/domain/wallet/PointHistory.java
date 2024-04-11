package com.example.performance_reservation.domain.wallet;

import com.example.performance_reservation.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PointHistory extends BaseEntity {
    @Id
    @GeneratedValue
    private long id;

    private long walletId;

    @Enumerated(EnumType.STRING)
    private PointActivity activity;
}
