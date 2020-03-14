package com.app.common.exception;

public class TokenException extends RuntimeException {
    public TokenException(String msg) {
        super(msg);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }

}
