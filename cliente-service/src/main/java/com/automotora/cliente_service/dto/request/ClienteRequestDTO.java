package com.automotora.cliente_service.dto.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteRequestDTO {

    private String rut;
    private String dv;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private LocalDate fechaRegistro;
    private String estado;
    private String usuarioId;
}