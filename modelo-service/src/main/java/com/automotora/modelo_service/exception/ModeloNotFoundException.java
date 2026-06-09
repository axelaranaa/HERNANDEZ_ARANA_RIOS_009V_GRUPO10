package com.automotora.modelo_service.exception;

public class ModeloNotFoundException extends RuntimeException {

    public ModeloNotFoundException(String message) {
        super(message);
    }
}