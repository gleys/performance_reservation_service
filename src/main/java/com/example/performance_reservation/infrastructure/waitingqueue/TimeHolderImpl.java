package com.example.performance_reservation.infrastructure.waitingqueue;

import com.example.performance_reservation.domain.waitingqueue.TimeHolder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Profile("dev")
@Component
public class TimeHolderImpl implements TimeHolder {
    @Override
    public LocalDateTime getExpiredTime() {
        return LocalDateTime.now().plusMinutes(5);
    }
}
