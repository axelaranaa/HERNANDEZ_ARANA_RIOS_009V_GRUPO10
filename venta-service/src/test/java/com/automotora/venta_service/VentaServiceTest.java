package com.automotora.venta_service;

import com.automotora.venta_service.dto.request.VentaRequestDTO;
import com.automotora.venta_service.dto.response.VentaResponseDTO;
import com.automotora.venta_service.model.Venta;
import com.automotora.venta_service.repository.VentaRepository;
import com.automotora.venta_service.service.VentaService;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test") // Usa tu archivo application-test.yml
class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private VentaService ventaService;

    @Test
    @DisplayName("Dado un VentaRequestDTO válido, cuando se guarda, entonces retorna el VentaResponseDTO correcto")
    void guardarVentaExitosamente() {
        // GIVEN (Preparación de datos usando Builder)
        VentaRequestDTO requestDTO = VentaRequestDTO.builder()
                .montoTotal(15000000.0)
                .estadoVenta("COMPLETA")
                .clienteId("CLI-123")
                .vehiculoId("VEH-456")
                .build();

        Venta ventaGuardada = Venta.builder()
                .id("UUID-VENTA-001")
                .montoTotal(15000000.0)
                .estadoVenta("COMPLETA")
                .clienteId("CLI-123")
                .vehiculoId("VEH-456")
                .build();

        // Simulación del comportamiento del repositorio mockeado
        when(ventaRepository.save(any(Venta.class))).thenReturn(ventaGuardada);

        // WHEN (Ejecución del método real del servicio)
        VentaResponseDTO respuesta = ventaService.guardar(requestDTO);

        // THEN (Validaciones estrictas de la rúbrica)
        assertNotNull(respuesta, "La respuesta no debería ser nula");
        assertEquals("UUID-VENTA-001", respuesta.getId());
        assertEquals("COMPLETA", respuesta.getEstadoVenta());
        assertEquals(15000000.0, respuesta.getMontoTotal());
        
        // Verifica que interactuó con la base de datos simulada exactamente 1 vez
        verify(ventaRepository, times(1)).save(any(Venta.class));
    }

    @Test
    @DisplayName("Dado un ID inexistente, cuando se busca la venta, entonces lanza IllegalArgumentException")
    void obtenerPorIdLanzaExcepcionCuandoNoExiste() {
        // GIVEN
        String idInexistente = "ID-FALSO";
        when(ventaRepository.findById(idInexistente)).thenReturn(Optional.empty());

        // WHEN & THEN (Se ejecuta y se intercepta la excepción esperada)
        IllegalArgumentException excepcion = assertThrows(IllegalArgumentException.class, () -> {
            ventaService.obtenerPorId(idInexistente);
        });

        // Verificación del mensaje de error que exige el indicador
        assertEquals("Venta no encontrada con ID: " + idInexistente, excepcion.getMessage());
        
        // Comprueba que el flujo se detuvo y leyó el repositorio una sola vez
        verify(ventaRepository, times(1)).findById(idInexistente);
    }
}