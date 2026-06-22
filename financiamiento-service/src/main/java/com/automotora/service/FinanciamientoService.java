package com.automotora.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automotora.dto.request.FinanciamientoRequestDTO;   
import com.automotora.dto.response.FinanciamientoResponseDTO; 
import com.automotora.exception.ResourceNotFoundException; 
import com.automotora.model.Financiamiento; 
import com.automotora.repository.FinanciamientoRepository; 
import com.automotora.client.VentaClient; 
import com.automotora.dto.VentaDTO;      

import java.util.UUID;

@Service
public class FinanciamientoService {
    private static final Logger logger = LoggerFactory.getLogger(FinanciamientoService.class);

    @Autowired
    private FinanciamientoRepository repository;

    @Autowired
    private VentaClient ventaClient; 

    // Ahora recibe el RequestDTO y devuelve el ResponseDTO
    public FinanciamientoResponseDTO procesarFinanciamiento(FinanciamientoRequestDTO request) {
        logger.info("Procesando financiamiento para la venta ID: {}", request.getVentaId());

        // 1. Validar si la venta existe antes de otorgar el crédito
        try {
            logger.info("Consultando a venta-service por la venta ID: {}...", request.getVentaId());
            VentaDTO venta = ventaClient.obtenerVentaPorId(request.getVentaId());
            
            if (venta == null) {
                throw new ResourceNotFoundException("No se puede financiar. La venta con ID " + request.getVentaId() + " no existe.");
            }
            logger.info("Venta encontrada con éxito. Continuando con el proceso de financiamiento...");

        } catch (ResourceNotFoundException e) {
            throw e; // Deja pasar el 404 limpio hacia el GlobalExceptionHandler
        } catch (Exception e) {
            logger.error("Error de comunicación con venta-service desde Financiamiento: {}", e.getMessage());
            throw new RuntimeException("No se pudo procesar el financiamiento: El servicio de ventas no responde en este momento.");
        }

        // 2. Pasamos los datos del RequestDTO a la Entidad para guardarla en BD
        Financiamiento financiamiento = new Financiamiento();
        financiamiento.setId(UUID.randomUUID().toString());
        financiamiento.setVentaId(request.getVentaId());
        financiamiento.setMontoFinanciado(request.getMontoFinanciado());
        financiamiento.setCuotas(request.getCuotas());

        Financiamiento guardado = repository.save(financiamiento);
        logger.info("Financiamiento registrado de forma exitosa con ID: {}", guardado.getId());

        // 3. Convertimos la entidad guardada en el ResponseDTO de salida
        return mapearAResponse(guardado);
    }

    // Método auxiliar para transformar Entidad -> DTO de respuesta
    private FinanciamientoResponseDTO mapearAResponse(Financiamiento financiamiento) {
        FinanciamientoResponseDTO response = new FinanciamientoResponseDTO();
        response.setId(financiamiento.getId());
        response.setVentaId(financiamiento.getVentaId());
        response.setMontoFinanciado(financiamiento.getMontoFinanciado());
        response.setCuotas(financiamiento.getCuotas());
        return response;
    }
}