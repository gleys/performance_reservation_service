package com.example.performance_reservation.domain.reservation.exception;

import com.example.performance_reservation.domain.reservation.exception.errorcode.ReservationErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class IllegalReservationStateException extends DefaultException {
    public static DefaultException Exception = new IllegalReservationStateException();

    public IllegalReservationStateException() {
        super(ReservationErrorCode.RESERVATION_STATE_INVALID);
    }

}
