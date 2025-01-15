package com.yiyoalfredo.mishorariosteclaguna.excepcion;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de InvalidCredentialsException
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<String> handleInvalidCredentialsException(InvalidCredentialsException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED) // Código 401
                .body(ex.getMessage());
    }

    // Manejo de LoginException
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<String> handleLoginException(LoginException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // Código 500
                .body(ex.getMessage());
    }

    // Manejo genérico de otras excepciones
    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleException(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR) // Código 500
                .body("Ocurrió un error inesperado. Por favor, inténtelo más tarde.");
    }
}
