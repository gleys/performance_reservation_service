package com.example.performance_reservation.domain.performance;


import com.example.performance_reservation.domain.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Performance extends BaseEntity {
    @Id
    @GeneratedValue
    private long id;

    private String title;

    private String performer;

    @OneToMany(mappedBy = "performance")
    private List<PerformanceDetail> performanceDetails;

    private int price;

    public String getTitle() {
        return null;
    }

    public String getPerformer() {
        return null;
    }

    public int getPrice() {
        return 0;
    }




}
