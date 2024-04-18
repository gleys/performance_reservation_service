package com.example.performance_reservation.domain.wallet.exception;

import com.example.performance_reservation.domain.wallet.exception.errorcode.WalletErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class AmountInvalidException extends DefaultException {
    public static DefaultException EXCEPTION = new AmountInvalidException();
    public AmountInvalidException() {
        super(WalletErrorCode.AMOUNT_INSUFFICIENT);
    }
}
