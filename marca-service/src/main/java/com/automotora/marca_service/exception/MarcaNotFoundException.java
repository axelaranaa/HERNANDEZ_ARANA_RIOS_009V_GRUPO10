package com.automotora.marca_service.exception;

public class MarcaNotFoundException extends RuntimeException {

    public MarcaNotFoundException(String message) {
        super(message);
    }
}