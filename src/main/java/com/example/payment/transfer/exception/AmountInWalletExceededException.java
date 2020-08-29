package com.example.payment.transfer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class AmountInWalletExceededException  extends RuntimeException {

    private static final long serialVersionUID = -828126129108947213L;

    public AmountInWalletExceededException(String message)
    {
        super(message);
    }

    public AmountInWalletExceededException(String message, Throwable cause)
    {
        super(message, cause);
    }

    public AmountInWalletExceededException() {

    }
}
