package com.automotora.venta_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.automotora.venta_service.config.FeignConfig;

/**
 * Cliente Feign para comunicarse con el microservicio 'venta-service'.
 * 'name' debe coincidir exactamente con el spring.application.name registrado en Eureka.
 * 'configuration' aplica las propiedades de Logs que creamos en FeignConfig.
 */
@FeignClient(name = "venta-service", configuration = FeignConfig.class)
public interface VentaClient {

    /**
     * Llama al endpoint GET http://venta-service/api/ventas/{id}
     * Sirve para verificar de forma síncrona si la venta existe en el otro microservicio.
     */
    @GetMapping("/api/ventas/{id}")
    Object obtenerVenta(@PathVariable("id") String id); 
    // Usamos 'Object' genérico porque a pago-service solo le interesa saber 
    // si responde un HTTP 200 OK (existe) o lanza un HTTP 404 (no existe).
}