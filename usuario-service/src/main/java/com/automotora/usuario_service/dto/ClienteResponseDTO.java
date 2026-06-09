package com.automotora.usuario_service.dto;

import lombok.Data;

@Data
public class ClienteResponseDTO {

    private String id;
    private String rut;
    private String dv;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private String estado;
}