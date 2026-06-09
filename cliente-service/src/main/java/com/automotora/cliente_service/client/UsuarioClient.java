package com.automotora.cliente_service.client;

import com.automotora.cliente_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "usuario-service",
        url = "http://localhost:8091",
        configuration = FeignConfig.class
)
public interface UsuarioClient {

    @GetMapping("/api/usuarios/{id}")
    Object obtenerUsuario(
            @PathVariable("id") String id);
}