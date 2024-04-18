package com.example.performance_reservation.error;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum GlobalErrorCode implements BaseErrorCode {
    UNKNOWN("UNKNOWN", "잠시 후 다시 시도해주세요.");

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