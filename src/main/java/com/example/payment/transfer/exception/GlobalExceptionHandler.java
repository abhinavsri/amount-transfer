package com.example.payment.transfer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Objects;
import java.util.stream.Collectors;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {



    @ExceptionHandler(UserNotFoundException.class)
    public final ResponseEntity<ErrorDetail> handleUserNotFoundException(UserNotFoundException ex,
                                                                                                        WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(AmountInWalletExceededException.class)
    public final ResponseEntity<ErrorDetail> handleAmountInWalletExceededException(AmountInWalletExceededException ex,
                                                                                                        WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(SelfDepositException.class)
    public final ResponseEntity<ErrorDetail> handleSelfDepositException(SelfDepositException ex,
                                                                                                        WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(TransferException.class)
    public final ResponseEntity<ErrorDetail> handleTransferException(TransferException ex,
                                                                         WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);

    }


    @ExceptionHandler(WalletNotFoundException.class)
    public final ResponseEntity<ErrorDetail> handleWalletNotFoundException(WalletNotFoundException ex,
                                                                     WebRequest request) {
        ErrorDetail errorDetail = new ErrorDetail(new Date(), ex.getMessage());
        return new ResponseEntity<>(errorDetail, HttpStatus.BAD_REQUEST);

    }
}
