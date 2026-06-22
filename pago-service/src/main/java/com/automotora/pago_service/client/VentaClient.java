package com.automotora.pago_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "venta-service")
public interface VentaClient {

    // Quitamos el /v1/ porque tu VentaController está mapeado como /api/ventas
    @GetMapping("/api/ventas/{id}")
    Object obtenerVenta(@PathVariable("id") String id); 
}