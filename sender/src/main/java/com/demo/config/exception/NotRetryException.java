package com.demo.config.exception;

public class NotRetryException extends Exception {
    public NotRetryException(){}

    public NotRetryException(String message) {
        super(message);
    }
}
