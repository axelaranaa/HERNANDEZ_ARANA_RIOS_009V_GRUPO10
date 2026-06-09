package com.automotora.servicio_tecnico_service.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServicioTecnicoNotFoundException.class)
    public ResponseEntity<ErrorResponse> manejarNotFound(
            ServicioTecnicoNotFoundException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        ErrorResponse.builder()
                                .error("Servicio técnico no encontrado")
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .status(404)
                                .build()
                );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGeneral(
            Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.builder()
                                .error("Internal Server Error")
                                .message(ex.getMessage())
                                .timestamp(LocalDateTime.now())
                                .status(500)
                                .build()
                );
    }
}