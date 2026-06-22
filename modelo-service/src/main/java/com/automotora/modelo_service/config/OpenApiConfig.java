package com.automotora.modelo_service.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI modeloServiceOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Modelo Service API")
                        .description("Microservicio para gestión de modelos de vehículos")
                        .version("1.0"));
    }
}