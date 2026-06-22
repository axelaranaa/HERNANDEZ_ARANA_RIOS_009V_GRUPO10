package com.automotora.exception;



import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//  Esta anotación le avisa a Spring que este error corresponde a un código HTTP 404
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {
    
    // Constructor que recibe el mensaje personalizado que queremos mostrar
    public ResourceNotFoundException(String mensaje) {
        super(mensaje);
    }
}