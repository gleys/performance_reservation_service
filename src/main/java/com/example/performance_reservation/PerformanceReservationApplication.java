package com.example.performance_reservation;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class PerformanceReservationApplication {

    public static void main(String[] args) {
        SpringApplication.run(PerformanceReservationApplication.class, args);
    }

}
