package com.example.performance_reservation.domain.wallet;

import com.example.performance_reservation.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class PointHistory extends BaseEntity {
    @Id
    @GeneratedValue
    private long id;

    @Getter
    private long userId;

    @Getter
    private int amount;

    @Getter
    @Enumerated(EnumType.STRING)
    private PointActivity activity;

    public PointHistory(final long userId, final int amount, final PointActivity activity) {
        this.userId = userId;
        this.amount = amount;
        this.activity = activity;
    }

    public static PointHistory record(final long userId, final int amount, final PointActivity activity) {
        return new PointHistory(userId, amount, activity);
    }

}