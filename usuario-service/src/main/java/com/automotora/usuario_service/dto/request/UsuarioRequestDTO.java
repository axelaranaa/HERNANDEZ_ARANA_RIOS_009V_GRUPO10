package com.automotora.usuario_service.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UsuarioRequestDTO {

    @NotBlank(message = "El username es obligatorio")
    private String username;

    @Email(message = "Correo inválido")
    @NotBlank(message = "El email es obligatorio")
    private String email;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;

    @NotBlank(message = "El estado es obligatorio")
    private String estado;

    @NotBlank(message = "El rol es obligatorio")
    private String rol;
}
