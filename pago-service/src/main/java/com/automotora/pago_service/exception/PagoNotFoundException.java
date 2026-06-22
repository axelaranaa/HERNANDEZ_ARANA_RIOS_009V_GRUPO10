package com.automotora.pago_service.exception;

public class PagoNotFoundException extends RuntimeException {

    public PagoNotFoundException(String mensaje) {
        super(mensaje);
    }

}