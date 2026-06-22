package com.automotora.vehiculo_service.exception;

public class ServicioExternoNoDisponibleException extends RuntimeException {
    public ServicioExternoNoDisponibleException(String message) {
        super(message);
    }
}