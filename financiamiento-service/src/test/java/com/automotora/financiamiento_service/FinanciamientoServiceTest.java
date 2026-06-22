package com.automotora.financiamiento_service;

import com.automotora.financiamiento_service.dto.request.FinanciamientoRequestDTO;
import com.automotora.financiamiento_service.dto.response.FinanciamientoResponseDTO;
import com.automotora.financiamiento_service.exception.FinanciamientoNotFoundException;
import com.automotora.financiamiento_service.model.Financiamiento;
import com.automotora.financiamiento_service.repository.FinanciamientoRepository;
import com.automotora.financiamiento_service.service.FinanciamientoService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
class FinanciamientoServiceTest {

    @Mock
    private FinanciamientoRepository financiamientoRepository;

    @InjectMocks
    private FinanciamientoService financiamientoService; // <-- Esta es la instancia que debemos usar

    @Test
    @DisplayName("Dado una solicitud válida, cuando se procesa, se registra el financiamiento con éxito")
    void crearFinanciamientoExitoso() {
        // GIVEN
        FinanciamientoRequestDTO request = FinanciamientoRequestDTO.builder()
                .montoSolicitado(8500000.0)
                .cantidadCuotas(24)
                .tasaInteres(1.5)
                .estadoSolicitud("PENDIENTE")
                .fechaSolicitud(LocalDate.now())
                .ventaId("VENTA-999")
                .build();

        Financiamiento guardado = Financiamiento.builder()
                .id("FINAN-001")
                .montoSolicitado(8500000.0)
                .cantidadCuotas(24)
                .tasaInteres(1.5)
                .estadoSolicitud("PENDIENTE")
                .fechaSolicitud(LocalDate.now())
                .ventaId("VENTA-999")
                .build();

        when(financiamientoRepository.save(any(Financiamiento.class))).thenReturn(guardado);

        // WHEN
        FinanciamientoResponseDTO response = financiamientoService.crear(request);

        // THEN
        assertNotNull(response);
        assertEquals("FINAN-001", response.getId());
        assertEquals("VENTA-999", response.getVentaId());
        verify(financiamientoRepository, times(1)).save(any(Financiamiento.class));
    }

    @Test
    @DisplayName("Dado un ID inexistente, cuando se busca el financiamiento, lanza FinanciamientoNotFoundException")
    void buscarFinanciamientoInexistenteLanzaExcepcion() {
        // GIVEN
        Mockito.when(financiamientoRepository.findById("ID-FALSO")).thenReturn(Optional.empty());

        // WHEN & THEN
        assertThrows(FinanciamientoNotFoundException.class, () -> {
            // CORREGIDO: Usamos la variable 'financiamientoService' con minúscula inicial (la instancia inyectada)
            financiamientoService.obtenerPorId("ID-FALSO"); 
        });

        verify(financiamientoRepository, times(1)).findById("ID-FALSO");
    }
}