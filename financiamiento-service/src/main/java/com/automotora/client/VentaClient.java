package com.automotora.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.automotora.dto.VentaDTO;

//  Le decimos a Feign que apunte al contenedor de Docker "venta-service" en su puerto 8081
@FeignClient(name = "venta-service", url = "http://venta-service:8081/api/v1/ventas")
public interface VentaClient {

    //  Este método hace un GET a http://venta-service:8081/api/v1/ventas/{id}
    @GetMapping("/{id}")
    VentaDTO obtenerVentaPorId(@PathVariable("id") String id);
}