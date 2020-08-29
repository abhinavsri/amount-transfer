package com.example.payment.transfer.exception;


import java.util.Date;


public class ErrorDetail {
    private Date timestamp;
    private String message;

    public ErrorDetail(Date timestamp, String message) {
        this.timestamp = timestamp;
        this.message = message;
    }
}