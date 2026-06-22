package com.automotora.pago_service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.automotora.pago_service.client.VentaClient;
import com.automotora.pago_service.dto.request.PagoRequestDTO;
import com.automotora.pago_service.dto.response.PagoResponseDTO;
import com.automotora.pago_service.model.Pago;
import com.automotora.pago_service.repository.PagoRepository;
import com.automotora.pago_service.service.PagoService;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class PagoServiceTest {

    @Mock
    private PagoRepository pagoRepository;

    @Mock
    private VentaClient ventaClient; 

    @InjectMocks
    private PagoService pagoService; // CORREGIDO: Apuntando a PagoService y no a la Application class

    @Test
    @DisplayName("Dado un PagoRequestDTO con venta existente, cuando se crea el pago, entonces lo registra con éxito")
    void crearPagoExitosamente() {
        // GIVEN
        PagoRequestDTO requestDTO = new PagoRequestDTO();
        requestDTO.setMetodoPago("EFECTIVO");
        requestDTO.setMontoAbonado(500000.0);
        requestDTO.setFechaPago(LocalDate.now());
        requestDTO.setEstadoPago("APROBADO");
        requestDTO.setVentaId("VENTA-EXISTENTE-123");

        Pago pagoGuardado = Pago.builder()
                .id("PAGO-001")
                .metodoPago("EFECTIVO")
                .montoAbonado(500000.0)
                .fechaPago(LocalDate.now())
                .estadoPago("APROBADO")
                .ventaId("VENTA-EXISTENTE-123")
                .build();

        doNothing().when(ventaClient).obtenerVenta("VENTA-EXISTENTE-123");
        when(pagoRepository.save(any(Pago.class))).thenReturn(pagoGuardado);

        // WHEN
        PagoResponseDTO respuesta = pagoService.crear(requestDTO);

        // THEN
        assertNotNull(respuesta);
        assertEquals("PAGO-001", respuesta.getId());
        assertEquals("VENTA-EXISTENTE-123", respuesta.getVentaId());
        
        verify(ventaClient, times(1)).obtenerVenta("VENTA-EXISTENTE-123");
        verify(pagoRepository, times(1)).save(any(Pago.class));
    }

    @Test
    @DisplayName("Dado un ventaId que NO existe, cuando se crea el pago, entonces lanza IllegalArgumentException")
    void crearPagoLanzaExcepcionCuandoVentaNoExiste() {
        // GIVEN
        PagoRequestDTO requestDTO = new PagoRequestDTO();
        requestDTO.setVentaId("VENTA-INEXISTENTE");

        doThrow(new RuntimeException("404 Not Found")).when(ventaClient).obtenerVenta("VENTA-INEXISTENTE");

        // WHEN & THEN
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            pagoService.crear(requestDTO);
        });

        assertEquals("No se puede registrar el pago. La venta con ID 'VENTA-INEXISTENTE' no existe.", excepcion.getMessage());
        verify(pagoRepository, never()).save(any(Pago.class));
    }
}