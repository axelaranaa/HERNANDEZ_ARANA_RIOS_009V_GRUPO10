package com.automotora.financiamiento_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.automotora.financiamiento_service.dto.request.FinanciamientoRequestDTO;
import com.automotora.financiamiento_service.dto.response.FinanciamientoResponseDTO;
import com.automotora.financiamiento_service.exception.FinanciamientoNotFoundException;
import com.automotora.financiamiento_service.model.Financiamiento;
import com.automotora.financiamiento_service.repository.FinanciamientoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FinanciamientoService {

    private final FinanciamientoRepository financiamientoRepository;

    @Transactional(readOnly = true)
    public List<FinanciamientoResponseDTO> obtenerTodos() {
        log.info("Servicio: Obteniendo todos los registros de financiamiento");
        return financiamientoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public FinanciamientoResponseDTO obtenerPorId(String id) {
        log.info("Servicio: Buscando financiamiento con ID: {}", id);
        Financiamiento financiamiento = financiamientoRepository.findById(id)
                .orElseThrow(() -> new FinanciamientoNotFoundException("Financiamiento no encontrado con ID: " + id));
        return convertirADTO(financiamiento);
    }

    @Transactional
    public FinanciamientoResponseDTO crear(FinanciamientoRequestDTO dto) {
        log.info("Servicio: Creando nueva solicitud de financiamiento para la venta ID: {}", dto.getVentaId());
        
        Financiamiento financiamiento = Financiamiento.builder()
                .montoSolicitado(dto.getMontoSolicitado())
                .cantidadCuotas(dto.getCantidadCuotas())
                .tasaInteres(dto.getTasaInteres())
                .estadoSolicitud(dto.getEstadoSolicitud())
                .fechaSolicitud(dto.getFechaSolicitud())
                .ventaId(dto.getVentaId())
                .build();

        Financiamiento guardado = financiamientoRepository.save(financiamiento);
        return convertirADTO(guardado);
    }

    @Transactional
    public FinanciamientoResponseDTO actualizar(String id, FinanciamientoRequestDTO dto) {
        log.info("Servicio: Actualizando financiamiento con ID: {}", id);
        Financiamiento financiamiento = financiamientoRepository.findById(id)
                .orElseThrow(() -> new FinanciamientoNotFoundException("Financiamiento no encontrado con ID: " + id));

        financiamiento.setMontoSolicitado(dto.getMontoSolicitado());
        financiamiento.setCantidadCuotas(dto.getCantidadCuotas());
        financiamiento.setTasaInteres(dto.getTasaInteres());
        financiamiento.setEstadoSolicitud(dto.getEstadoSolicitud());
        financiamiento.setFechaSolicitud(dto.getFechaSolicitud());
        financiamiento.setVentaId(dto.getVentaId());

        return convertirADTO(financiamientoRepository.save(financiamiento));
    }

    @Transactional
    public void eliminar(String id) {
        log.info("Servicio: Eliminando financiamiento con ID: {}", id);
        Financiamiento financiamiento = Financiamiento.findById(id)
                .orElseThrow(() -> new FinanciamientoNotFoundException("Financiamiento no encontrado con ID: " + id));
        financiamientoRepository.delete(financiamiento);
    }

    private FinanciamientoResponseDTO convertirADTO(Financiamiento f) {
        return FinanciamientoResponseDTO.builder()
                .id(f.getId())
                .montoSolicitado(f.getMontoSolicitado())
                .cantidadCuotas(f.getCantidadCuotas())
                .tasaInteres(f.getTasaInteres())
                .estadoSolicitud(f.getEstadoSolicitud())
                .fechaSolicitud(f.getFechaSolicitud())
                .ventaId(f.getVentaId())
                .build();
    }
}