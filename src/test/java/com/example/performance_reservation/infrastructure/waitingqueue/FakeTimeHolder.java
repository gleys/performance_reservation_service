package com.example.performance_reservation.infrastructure.waitingqueue;

import com.example.performance_reservation.domain.waitingqueue.TimeHolder;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Profile("test")
@Component
public class FakeTimeHolder implements TimeHolder {

    private LocalDateTime time;
    @Override
    public LocalDateTime getExpiredTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
