package com.automotora.servicio_tecnico_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ServicioTecnicoServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServicioTecnicoServiceApplication.class, args);
    }
}