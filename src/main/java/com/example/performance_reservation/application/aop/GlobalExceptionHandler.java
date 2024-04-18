package com.example.performance_reservation.application.aop;

import com.example.performance_reservation.domain.waitingqueue.exception.DefaultTokenException;
import com.example.performance_reservation.error.DefaultException;
import com.example.performance_reservation.error.ErrorResponse;
import com.example.performance_reservation.error.GlobalErrorCode;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException e) {
        return ResponseEntity.status(500).body(new DefaultException(GlobalErrorCode.UNKNOWN).getErrorResponse());
    }

    @ExceptionHandler(DefaultTokenException.class)
    protected ResponseEntity<ErrorResponse> handleDefaultTokenException(DefaultException e) {
        return ResponseEntity.status(401).body(e.getErrorResponse());
    }

    @ExceptionHandler(DefaultException.class)
    protected ResponseEntity<ErrorResponse> handleDefaultException(DefaultException e) {
        return ResponseEntity.status(200).body(e.getErrorResponse());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ErrorResponse> handleOtherException(DefaultException e) {
        return ResponseEntity.status(500).body(new DefaultException(GlobalErrorCode.UNKNOWN).getErrorResponse());
    }

}