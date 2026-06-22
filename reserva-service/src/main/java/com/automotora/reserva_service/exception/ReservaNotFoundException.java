package com.automotora.reserva_service.exception;

public class ReservaNotFoundException extends RuntimeException {

    public ReservaNotFoundException(String mensaje) {
        super(mensaje);
    }
}
