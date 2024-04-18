package com.example.performance_reservation.domain.performance.exception;

import com.example.performance_reservation.domain.performance.exception.errorcode.PerformanceErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class PerformanceNotReservableException extends DefaultException {
    public static DefaultException Exception = new PerformanceNotReservableException();

    public PerformanceNotReservableException() {
        super(PerformanceErrorCode.PERFORMANCE_NOT_RESERVABLE);
    }

}
