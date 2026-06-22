package com.automotora.venta_service.exception;

/**
 * Excepción personalizada para lanzar cuando una venta 
 * no exista en la base de datos de la automotora.
 */
public class VentaNotFoundException extends RuntimeException {
    
    public VentaNotFoundException(String mensaje) {
        super(mensaje);
    }
}