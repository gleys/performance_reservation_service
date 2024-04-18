package com.example.performance_reservation.domain.waitingqueue.exception;

import com.example.performance_reservation.domain.waitingqueue.exception.errorcode.WaitingQueueErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class InvalidTokenException extends DefaultException {
    public static DefaultException Exception = new InvalidTokenException();

    public InvalidTokenException() {
        super(WaitingQueueErrorCode.TOKEN_INVALID);
    }
}

