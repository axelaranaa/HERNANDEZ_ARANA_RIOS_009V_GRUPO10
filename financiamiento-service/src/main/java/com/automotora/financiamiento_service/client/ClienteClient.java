package com.automotora.financiamiento_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.automotora.financiamiento_service.config.FeignConfig;

@FeignClient(
    name = "cliente-service", // Nombre del microservicio destino (para Eureka/API Gateway)
    url = "${application.config.cliente-url:http://localhost:8082/v1/clientes}", // URL por defecto si corre local
    configuration = FeignConfig.class // Reutiliza la configuración de logs y decodificador de errores que ya creamos
)
public interface ClienteClient {

    /**
     * Realiza una petición GET síncrona a 'cliente-service' para verificar 
     * si el cliente existe utilizando su ID único.
     * * @param id Identificador único del cliente
     */
    @GetMapping("/{id}")
    void obtenerCliente(@PathVariable("id") String id);
}