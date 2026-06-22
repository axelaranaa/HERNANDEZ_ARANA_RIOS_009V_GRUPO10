package com.automotora.financiamiento_service.config;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CustomFeignErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder defaultErrorDecoder = new ErrorDecoder.Default();

    @Override
    public Exception decode(String methodKey, Response response) {
        log.error("Feign falló al invocar: {} - Estado HTTP: {}", methodKey, response.status());

        switch (response.status()) {
            case 400:
                return new IllegalArgumentException("Petición incorrecta enviada al microservicio externo.");
            case 404:
                return new IllegalArgumentException("El recurso solicitado en el microservicio externo no fue encontrado.");
            default:
                // Para cualquier otro error (como un 500), usamos el manejador por defecto de Feign
                return defaultErrorDecoder.decode(methodKey, response);
        }
    }
}