package com.example.autoconfigure.client.exception;

public class HttpCallException extends RuntimeException {
    public HttpCallException(String message) {
        super(message);
    }

    public HttpCallException(String message, Throwable cause) {
        super(message, cause);
    }
}
