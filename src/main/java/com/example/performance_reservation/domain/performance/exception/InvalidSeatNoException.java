package com.example.performance_reservation.domain.performance.exception;

import com.example.performance_reservation.domain.performance.exception.errorcode.PerformanceErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class InvalidSeatNoException extends DefaultException {
    public static DefaultException Exception = new InvalidSeatNoException();

    public InvalidSeatNoException() {
        super(PerformanceErrorCode.SEAT_NO_INVALID);
    }
}