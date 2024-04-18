package com.example.performance_reservation.domain.waitingqueue.exception;

import com.example.performance_reservation.domain.waitingqueue.exception.errorcode.WaitingQueueErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class TokenNotFoundException extends DefaultTokenException {
    public static DefaultException Exception = new TokenNotFoundException();
    public TokenNotFoundException() {
        super(WaitingQueueErrorCode.TOKEN_NOT_FOUND);
    }
}
