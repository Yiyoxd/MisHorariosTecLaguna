package com.yiyoalfredo.mishorariosteclaguna.excepcion;

public class LoginException extends Exception {
    public LoginException(String message) {
        super(message);
    }

    public LoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
