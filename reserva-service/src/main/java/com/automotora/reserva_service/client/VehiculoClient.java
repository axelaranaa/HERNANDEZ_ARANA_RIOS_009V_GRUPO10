package com.automotora.reserva_service.client;

import com.automotora.reserva_service.dto.response.VehiculoResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "vehiculo-service",
        url = "${vehiculo.service.url}"
)
public interface VehiculoClient {

    @GetMapping("/api/vehiculos/{id}")
    VehiculoResponseDTO obtenerVehiculo(@PathVariable String id);
}
