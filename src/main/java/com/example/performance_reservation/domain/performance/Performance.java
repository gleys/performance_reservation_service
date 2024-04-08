package com.example.performance_reservation.domain.performance;


import com.example.performance_reservation.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Performance extends BaseEntity {
    @Id
    @GeneratedValue
    private long id;

    private String title;

    private String performer;

    private int price;

}
