package com.example.performance_reservation.domain.reservation.exception;

import com.example.performance_reservation.domain.reservation.exception.errorcode.ReservationErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class AlreadyReservedException extends DefaultException {
    public static DefaultException Exception = new AlreadyReservedException();

    public AlreadyReservedException() {
        super(ReservationErrorCode.ALREADY_RESERVED);
    }

}
