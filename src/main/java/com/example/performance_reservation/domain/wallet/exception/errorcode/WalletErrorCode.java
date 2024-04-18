package com.example.performance_reservation.domain.wallet.exception.errorcode;

import com.example.performance_reservation.error.BaseErrorCode;
import com.example.performance_reservation.error.ErrorResponse;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum WalletErrorCode implements BaseErrorCode {
    AMOUNT_INSUFFICIENT("AMOUNT_INSUFFICIENT", "잔액이 부족합니다."),
    AMOUNT_INVALID("AMOUNT_INVALID", "충전 포인트는 0 보다 커야 합니다."),
    WALLET_NOT_FOUND("WALLET_NOT_FOUND", "지갑 정보가 존재하지 않습니다. 관리자한테 문의 바랍니다.");

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
