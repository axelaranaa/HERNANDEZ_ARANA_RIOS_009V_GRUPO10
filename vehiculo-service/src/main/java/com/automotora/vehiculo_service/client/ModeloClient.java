package com.automotora.vehiculo_service.client;

import com.automotora.vehiculo_service.dto.response.ModeloResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "modelo-service",
        url = "${modelo.service.url}"
)
public interface ModeloClient {

    @GetMapping("/api/modelos/{id}")
    ModeloResponseDTO obtenerModelo(@PathVariable String id);
}