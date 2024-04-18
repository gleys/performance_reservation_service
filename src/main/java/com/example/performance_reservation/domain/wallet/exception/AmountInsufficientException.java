package com.example.performance_reservation.domain.wallet.exception;

import com.example.performance_reservation.domain.wallet.exception.errorcode.WalletErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class AmountInsufficientException extends DefaultException {
    public static DefaultException EXCEPTION = new AmountInsufficientException();

    public AmountInsufficientException() {
        super(WalletErrorCode.AMOUNT_INSUFFICIENT);
    }
}
