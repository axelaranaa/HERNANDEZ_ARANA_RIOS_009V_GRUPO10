package com.automotora.servicio_tecnico_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "vehiculo-service",
        url = "http://localhost:8083"
)
public interface VehiculoClient {

    @GetMapping("/api/vehiculos/{id}")
    Object obtenerVehiculo(
            @PathVariable String id);
}