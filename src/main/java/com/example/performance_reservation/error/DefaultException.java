package com.example.performance_reservation.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DefaultException extends RuntimeException{
    private BaseErrorCode errorCode;
    public ErrorResponse getErrorResponse() {
        return this.errorCode.getErrorResponse();
    }

    @Override
    public String getMessage() {
        return errorCode.getMessage();
    }
}