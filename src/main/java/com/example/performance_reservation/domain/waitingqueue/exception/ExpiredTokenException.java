package com.example.performance_reservation.domain.waitingqueue.exception;

import com.example.performance_reservation.domain.waitingqueue.exception.errorcode.WaitingQueueErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class ExpiredTokenException extends DefaultTokenException {
    public static DefaultException Exception = new ExpiredTokenException();

    public ExpiredTokenException() {
        super(WaitingQueueErrorCode.TOKEN_EXPIRED);
    }
}
