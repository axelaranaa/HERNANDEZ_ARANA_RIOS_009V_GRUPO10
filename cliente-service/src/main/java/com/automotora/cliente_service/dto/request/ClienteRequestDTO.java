package com.automotora.cliente_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClienteRequestDTO {

    @NotBlank(message = "El rut es obligatorio")
    private String rut;

    @NotBlank(message = "El dv es obligatorio")
    private String dv;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    private String apellido;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    private String telefono;

    private String direccion;

    @NotNull(message = "La fecha de registro es obligatoria")
    private LocalDate fechaRegistro;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotBlank(message = "El usuarioId es obligatorio")
    private String usuarioId;
}
