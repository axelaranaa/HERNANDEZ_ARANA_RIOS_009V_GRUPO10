package com.automotora.pago_service.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.automotora.pago_service.client.VentaClient;
import com.automotora.pago_service.dto.request.PagoRequestDTO;
import com.automotora.pago_service.dto.response.PagoResponseDTO;
import com.automotora.pago_service.model.Pago;
import com.automotora.pago_service.repository.PagoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagoService {

    private final PagoRepository pagoRepository;
    private final VentaClient ventaClient; 

    @Transactional(readOnly = true)
    public List<PagoResponseDTO> obtenerTodos() {
        log.info("Obteniendo todos los pagos registrados");
        return pagoRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public PagoResponseDTO obtenerPorId(String id) {
        log.info("Buscando pago con id: {}", id);
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado con ID: " + id));
        return convertirADTO(pago);
    }

    @Transactional
    public PagoResponseDTO crear(PagoRequestDTO dto) {
        log.info("Validando existencia de la venta ID: {} vía OpenFeign", dto.getVentaId());
        
        try {
            ventaClient.obtenerVenta(dto.getVentaId());
        } catch (Exception e) {
            log.error("Error al validar venta en pago-service: Venta no encontrada o fuera de servicio");
            throw new IllegalArgumentException("No se puede registrar el pago. La venta con ID '" + dto.getVentaId() + "' no existe.");
        }

        log.info("Creando nuevo pago en el sistema");
        Pago pago = Pago.builder()
                .metodoPago(dto.getMetodoPago())
                .montoAbonado(dto.getMontoAbonado())
                .fechaPago(dto.getFechaPago())
                .estadoPago(dto.getEstadoPago())
                .ventaId(dto.getVentaId())
                .build();

        return convertirADTO(pagoRepository.save(pago));
    }

    @Transactional
    public PagoResponseDTO actualizar(String id, PagoRequestDTO dto) {
        log.info("Actualizando pago {}", id);
        
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado con ID: " + id));

        try {
            ventaClient.obtenerVenta(dto.getVentaId());
        } catch (Exception e) {
            log.error("Error al validar venta en la actualización");
            throw new IllegalArgumentException("No se puede actualizar el pago. La venta con ID '" + dto.getVentaId() + "' no existe.");
        }

        pago.setMetodoPago(dto.getMetodoPago());
        pago.setMontoAbonado(dto.getMontoAbonado());
        pago.setFechaPago(dto.getFechaPago());
        pago.setEstadoPago(dto.getEstadoPago());
        pago.setVentaId(dto.getVentaId());

        return convertirADTO(pagoRepository.save(pago));
    }

    @Transactional
    public void eliminar(String id) {
        log.info("Eliminando pago {}", id);
        Pago pago = pagoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Pago no encontrado con ID: " + id));
        
        pagoRepository.delete(pago);
    }

    private PagoResponseDTO convertirADTO(Pago pago) {
        PagoResponseDTO response = new PagoResponseDTO();
        response.setId(pago.getId());
        response.setMetodoPago(pago.getMetodoPago());
        response.setMontoAbonado(pago.getMontoAbonado());
        response.setFechaPago(pago.getFechaPago());
        response.setEstadoPago(pago.getEstadoPago());
        response.setVentaId(pago.getVentaId());
        return response;
    }
}