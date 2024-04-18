package com.example.performance_reservation.domain.reservation.shceduler;

import com.example.performance_reservation.domain.reservation.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


@EnableScheduling
@Component
public class ReservationScheduler {

    @Autowired
    private ReservationRepository reservationRepository;

    @Transactional
    @Scheduled(fixedDelay = 10000)
    public void deleteExpiredReservation() {
        reservationRepository.removeExpiredReservations();
    }

}
