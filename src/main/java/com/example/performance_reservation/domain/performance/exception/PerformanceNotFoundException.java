package com.example.performance_reservation.domain.performance.exception;

import com.example.performance_reservation.domain.performance.exception.errorcode.PerformanceErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class PerformanceNotFoundException extends DefaultException {
    public static DefaultException Exception = new PerformanceNotFoundException();

    public PerformanceNotFoundException() {
        super(PerformanceErrorCode.PERFORMANCE_NOT_FOUND);
    }
}