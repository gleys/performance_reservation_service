package com.example.performance_reservation.domain.performance.exception;

import com.example.performance_reservation.domain.performance.exception.errorcode.PerformanceErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class InvalidDateConditionException extends DefaultException {
    public static DefaultException Exception = new InvalidDateConditionException();

    public InvalidDateConditionException() {
        super(PerformanceErrorCode.INVALID_DATE_CONDITION);
    }
}