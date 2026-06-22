package com.automotora.venta_service.service;

import com.automotora.venta_service.dto.request.VentaRequestDTO;
import com.automotora.venta_service.dto.response.VentaResponseDTO;
import com.automotora.venta_service.model.Venta;
import com.automotora.venta_service.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VentaService {

    private final VentaRepository ventaRepository;

    @Transactional(readOnly = true)
    public List<VentaResponseDTO> obtenerTodos() {
        log.info("Obteniendo todas las ventas registradas");
        return ventaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public VentaResponseDTO obtenerPorId(String id) {
        log.info("Buscando venta con ID: {}", id);
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada con ID: " + id));
        return convertirADTO(venta);
    }

    @Transactional
    public VentaResponseDTO guardar(VentaRequestDTO dto) {
        log.info("Registrando una nueva venta en el sistema");
        
        Venta venta = Venta.builder()
                .montoTotal(dto.getMontoTotal())
                .estadoVenta(dto.getEstadoVenta())
                .clienteId(dto.getClienteId())
                .vehiculoId(dto.getVehiculoId())
                .build();

        Venta ventaGuardada = ventaRepository.save(venta);
        log.info("Venta guardada exitosamente con ID: {}", ventaGuardada.getId());
        
        return convertirADTO(ventaGuardada);
    }

    @Transactional
    public void eliminar(String id) {
        log.info("Eliminando venta con ID: {}", id);
        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Venta no encontrada con ID: " + id));
        
        ventaRepository.delete(venta);
    }

    // Método auxiliar privado para mapear la Entidad al DTO de respuesta
    private VentaResponseDTO convertirADTO(Venta venta) {
        return VentaResponseDTO.builder()
                .id(venta.getId())
                .montoTotal(venta.getMontoTotal())
                .estadoVenta(venta.getEstadoVenta())
                .clienteId(venta.getClienteId())
                .vehiculoId(venta.getVehiculoId())
                .build();
    }
}