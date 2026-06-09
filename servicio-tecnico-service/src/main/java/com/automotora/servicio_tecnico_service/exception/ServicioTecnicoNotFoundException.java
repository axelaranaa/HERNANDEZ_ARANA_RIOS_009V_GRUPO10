package com.automotora.servicio_tecnico_service.exception;

public class ServicioTecnicoNotFoundException
        extends RuntimeException {

    public ServicioTecnicoNotFoundException(String mensaje) {
        super(mensaje);
    }
}