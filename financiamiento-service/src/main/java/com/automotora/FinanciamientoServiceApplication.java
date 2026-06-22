package com.automotora;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.automotora.exception.ResourceNotFoundException;
import com.automotora.model.Financiamiento; 
import com.automotora.repository.FinanciamientoRepository; 
import com.automotora.client.VentaClient; 
import com.automotora.dto.VentaDTO;
import com.automotora.dto.request.FinanciamientoRequestDTO;
import com.automotora.dto.response.FinanciamientoResponseDTO;

import java.util.UUID;

@Service
public class FinanciamientoServiceApplication {
    private static final Logger logger = LoggerFactory.getLogger(FinanciamientoServiceApplication.class);

    @Autowired
    private FinanciamientoRepository repository;

    @Autowired
    private VentaClient ventaClient; 

    public FinanciamientoResponseDTO procesarFinanciamiento(FinanciamientoRequestDTO request) {
        logger.info("Procesando financiamiento para la venta ID: {}", request.getVentaId());

        // 1. Validar si la venta existe antes de otorgar el crédito usando Feign
        try {
            logger.info("Consultando a venta-service por la venta ID: {}...", request.getVentaId());
            VentaDTO venta = ventaClient.obtenerVentaPorId(request.getVentaId());
            
            if (venta == null) {
                throw new ResourceNotFoundException("No se puede financiar. La venta con ID " + request.getVentaId() + " no existe.");
            }
            logger.info("Venta encontrada con éxito. Continuando con el proceso de financiamiento...");

        } catch (ResourceNotFoundException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Error de comunicación con venta-service desde Financiamiento: {}", e.getMessage());
            throw new RuntimeException("No se pudo procesar el financiamiento: El servicio de ventas no responde en este momento.");
        }

        // 2. Mapear los datos del DTO a la Entidad de Base de Datos
        Financiamiento financiamiento = new Financiamiento();
        financiamiento.setId(UUID.randomUUID().toString());
        financiamiento.setVentaId(request.getVentaId());
        financiamiento.setMontoFinanciado(request.getMontoFinanciado());
        financiamiento.setCuotas(request.getCuotas());
        
        // Asignamos una tasa por defecto (ej: 1.5% o el valor que maneje tu lógica)
        financiamiento.setTasaInteres(1.5);

        // 3. Persistir en la base de datos db_financiamientos
        Financiamiento guardado = repository.save(financiamiento);
        logger.info("Financiamiento registrado de forma exitosa con ID: {}", guardado.getId());

        // 4. Mapear el resultado al ResponseDTO exigido por el Controller
        FinanciamientoResponseDTO response = new FinanciamientoResponseDTO();
        response.setId(guardado.getId());
        response.setVentaId(guardado.getVentaId());
        response.setMontoFinanciado(guardado.getMontoFinanciado());
        response.setCuotas(guardado.getCuotas());

        return response;
    }
}