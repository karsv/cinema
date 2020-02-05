package com.dev.cinema.exception;

public class DataProcessingException extends Exception {
    public DataProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DataProcessingException(String message) {
        super(message);
    }
}
