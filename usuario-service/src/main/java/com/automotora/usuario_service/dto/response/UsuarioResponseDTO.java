package com.automotora.usuario_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UsuarioResponseDTO {

    private String id;
    private String username;
    private String email;
    private String estado;
    private String rol;

}