package com.automotora.cliente_service.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UsuarioResponseDTO {
    private String id;
    private String username;
    private String email;
    private String estado;
    private String rol;
}
