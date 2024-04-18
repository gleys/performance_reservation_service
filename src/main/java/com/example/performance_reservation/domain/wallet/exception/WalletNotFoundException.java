package com.example.performance_reservation.domain.wallet.exception;

import com.example.performance_reservation.domain.wallet.exception.errorcode.WalletErrorCode;
import com.example.performance_reservation.error.DefaultException;

public class WalletNotFoundException extends DefaultException {
    public static DefaultException EXCEPTION = new WalletNotFoundException();
    public WalletNotFoundException() {
        super(WalletErrorCode.WALLET_NOT_FOUND);
    }
}
