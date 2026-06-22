package com.automotora.financiamiento_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients; // <-- ¡IMPORT FALTA AQUÍ!

@SpringBootApplication
@EnableFeignClients // Ahora sí Spring Boot activará Feign correctamente sin romper la compilación
public class FinanciamientoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(FinanciamientoServiceApplication.class, args);
    }
}