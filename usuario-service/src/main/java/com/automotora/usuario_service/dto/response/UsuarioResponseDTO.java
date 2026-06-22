package com.automotora.usuario_service.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

    private String id;
    private String username;
    private String email;
    private String estado;
    private String rol;
}
