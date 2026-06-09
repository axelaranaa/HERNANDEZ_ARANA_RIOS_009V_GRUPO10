package com.automotora.modelo_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients

public class ModeloServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModeloServiceApplication.class, args);
	}

}
