package com.yiyoalfredo.mishorariosteclaguna.excepcion;

public class InvalidCredentialsException extends LoginException {
    public InvalidCredentialsException(String message) {
        super(message);
    }
}