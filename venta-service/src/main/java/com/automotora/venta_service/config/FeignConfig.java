package com.automotora.venta_service.config;

import feign.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    /**
     * Define el nivel de logging para los clientes Feign.
     * Logger.Level.FULL registra:
     * - Las cabeceras (Headers) de la petición y respuesta.
     * - El cuerpo (Body) del mensaje (JSON enviado y recibido).
     * - Metadatos de la conexión (URLs, estados HTTP como 200, 404, etc.).
     */
    @Bean
    public Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL; 
    }
}