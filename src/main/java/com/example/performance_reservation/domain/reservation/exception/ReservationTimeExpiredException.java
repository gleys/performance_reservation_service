package com.example.performance_reservation.domain.reservation.exception;

import com.example.performance_reservation.domain.reservation.exception.errorcode.ReservationErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class ReservationTimeExpiredException extends DefaultException {
    public static DefaultException Exception = new ReservationTimeExpiredException();

    public ReservationTimeExpiredException() {
        super(ReservationErrorCode.RESERVATION_TIME_EXPIRED);
    }

}
