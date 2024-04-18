package com.example.performance_reservation.domain.reservation.exception;

import com.example.performance_reservation.domain.waitingqueue.exception.errorcode.WaitingQueueErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class ReservationNotFoundException extends DefaultException {
    public static DefaultException Exception = new ReservationNotFoundException();

    public ReservationNotFoundException() {
        super(WaitingQueueErrorCode.TOKEN_NOT_FOUND);
    }

}