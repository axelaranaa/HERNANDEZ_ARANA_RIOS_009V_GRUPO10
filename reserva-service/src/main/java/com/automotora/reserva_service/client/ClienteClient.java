package com.automotora.reserva_service.client;

import com.automotora.reserva_service.dto.response.ClienteResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "cliente-service",
        url = "${cliente.service.url}"
)
public interface ClienteClient {

    @GetMapping("/api/clientes/{id}")
    ClienteResponseDTO obtenerCliente(@PathVariable String id);
}
