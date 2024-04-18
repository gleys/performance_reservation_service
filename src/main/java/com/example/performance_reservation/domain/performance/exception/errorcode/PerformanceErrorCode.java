package com.example.performance_reservation.domain.performance.exception.errorcode;

import com.example.performance_reservation.error.BaseErrorCode;
import com.example.performance_reservation.error.ErrorResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum PerformanceErrorCode implements BaseErrorCode {
    PERFORMANCE_NOT_RESERVABLE("PERFORMANCE_NOT_RESERVABLE", "예약 가능 상태가 아닙니다."),
    SEAT_NO_INVALID("SEAT_NO_INVALID", "올바르지 않은 좌석번호 입니다."),
    PERFORMANCE_NOT_FOUND("PERFORMANCE_NOT_FOUND", "존재하지 않는 공연 입니다."),
    INVALID_DATE_CONDITION("INVALID_DATE_CONDITION", "조회 시작 날짜는 종료 날짜 이전 이어야 입니다.");

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
