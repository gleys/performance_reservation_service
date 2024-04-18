package com.example.performance_reservation.domain.waitingqueue.exception;

import com.example.performance_reservation.error.BaseErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class DefaultTokenException extends DefaultException {
    public DefaultTokenException(final BaseErrorCode errorCode) {
        super(errorCode);
    }
}
