package com.automotora.vehiculo_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "modelo-service",
        url = "http://localhost:8082"
)
public interface ModeloClient {

    @GetMapping("/api/modelos/{id}")
    Object obtenerModelo(@PathVariable String id);
}