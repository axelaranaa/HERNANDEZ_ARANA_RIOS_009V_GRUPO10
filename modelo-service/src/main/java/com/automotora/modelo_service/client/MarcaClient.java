package com.automotora.modelo_service.client;

import com.automotora.modelo_service.config.FeignConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "marca-service",
        url = "http://localhost:8081",
        configuration = FeignConfig.class
)
public interface MarcaClient {

    @GetMapping("/api/marcas/{id}")
    Object obtenerMarcaPorId(@PathVariable String id);
}