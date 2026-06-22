package com.automotora.modelo_service.client;

import com.automotora.modelo_service.dto.response.MarcaResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "marca-service",
        url = "${marca.service.url}"
)
public interface MarcaClient {

    @GetMapping("/api/marcas/{id}")
    MarcaResponseDTO obtenerMarcaPorId(@PathVariable String id);
}