package com.example.performance_reservation.domain.waitingqueue.exception.errorcode;

import com.example.performance_reservation.error.BaseErrorCode;
import com.example.performance_reservation.error.ErrorResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum WaitingQueueErrorCode implements BaseErrorCode {
    TOKEN_NOT_FOUND("TOKEN_NOT_FOUND", "존재하지 않는 토큰입니다."),
    TOKEN_EXPIRED("TOKEN_EXPIRED", "만료된 토큰 입니다."),
    TOKEN_INVALID("TOKEN_INVALID", "유효하지 않은 토큰 입니다.");

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