package com.automotora.pago_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Eliminamos el 'url' fijo. Ahora Feign le preguntará a Eureka dónde está "venta-service" dinámicamente.
@FeignClient(name = "venta-service")
public interface VentaClient {

    // Apuntamos a la nueva ruta /v1 que configuramos en el controlador de ventas
    @GetMapping("/api/v1/ventas/{id}")
    Object obtenerVenta(@PathVariable("id") String id); 
}