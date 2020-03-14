package com.app.common.exception;

public class PasswordNotMatchException extends RuntimeException {

    public PasswordNotMatchException(){
        super("Password not match");
    }

}
