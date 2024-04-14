package com.example.performance_reservation.domain.waitingqueue;

import java.time.LocalDateTime;

public interface TimeHolder {
    LocalDateTime getExpiredTime();
}
