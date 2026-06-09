package com.automotora.cliente_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ClienteResponseDTO {

    private String id;
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