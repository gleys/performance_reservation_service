package com.example.performance_reservation.domain.reservation.exception.errorcode;

import com.example.performance_reservation.error.BaseErrorCode;
import com.example.performance_reservation.error.ErrorResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public enum ReservationErrorCode implements BaseErrorCode {
    RESERVATION_TIME_EXPIRED("RESERVATION_TIME_EXPIRED", "결제 유효 시간이 만료 되었습니다."),
    RESERVATION_STATE_INVALID("RESERVATION_STATE_INVALID", "유효하지 않은 예약입니다."),
    ALREADY_RESERVED("ALREADY_RESERVED", "예약된 좌석 입니다."),
    RESERVATION_NOT_FOUND("RESERVATION_NOT_FOUND", "올바르지 않은 예약 번호 입니다.");


    private String code;
    private String message;

    @Override
    public ErrorResponse getErrorResponse() {
        return new ErrorResponse(this.code, this.message);
    }

    @Override
    public String getMessage() {
        return this.message;
    }



}
