package com.example.performance_reservation.domain.performance;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SeatInfo {
    @Id
    @GeneratedValue
    private long id;

    private int seatNo;

    @Enumerated(EnumType.STRING)
    private OccupancyState state;

    private long performanceDetailId;


}
