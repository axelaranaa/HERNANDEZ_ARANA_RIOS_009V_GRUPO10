package com.automotora.financiamiento_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.automotora.financiamiento_service.config.FeignConfig;

@FeignClient(
    name = "venta-service", 
    url = "${application.config.venta-url:http://localhost:8081/v1/ventas}",
    configuration = FeignConfig.class
)
public interface VentaClient {

    @GetMapping("/{id}")
    void obtenerVenta(@PathVariable("id") String id);
}