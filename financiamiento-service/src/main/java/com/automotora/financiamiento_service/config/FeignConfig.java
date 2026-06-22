package com.automotora.financiamiento_service.config;

import feign.Logger;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignConfig {

    /**
     * Configura el nivel de logs para las peticiones Feign.
     * FULL detallará las URLs, headers, cuerpo de envío y respuestas en la consola.
     * Ideal para el desarrollo y depuración exigido en proyectos complejos.
     */
    @Bean
    Logger.Level feignLoggerLevel() {
        return Logger.Level.FULL;
    }

    /**
     * Decodificador de errores personalizado.
     * Sirve para interceptar las respuestas con código de estado 4xx o 5xx 
     * de los otros microservicios y personalizarlas antes de que rompan el flujo.
     */
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomFeignErrorDecoder();
    }
}