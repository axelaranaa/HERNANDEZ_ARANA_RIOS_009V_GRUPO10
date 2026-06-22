package com.automotora.cliente_service.client;

import com.automotora.cliente_service.dto.response.UsuarioResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "usuario-service",
        url = "${usuario.service.url}"
)
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}")
    UsuarioResponseDTO obtenerUsuario(@PathVariable String id);
}
