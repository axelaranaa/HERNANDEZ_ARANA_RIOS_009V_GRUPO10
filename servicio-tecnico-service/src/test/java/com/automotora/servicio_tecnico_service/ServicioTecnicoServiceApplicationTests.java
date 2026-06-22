package com.automotora.servicio_tecnico_service;

import com.automotora.servicio_tecnico_service.client.VehiculoClient;
import com.automotora.servicio_tecnico_service.dto.request.ServicioTecnicoRequestDTO;
import com.automotora.servicio_tecnico_service.dto.response.ServicioTecnicoResponseDTO;
import com.automotora.servicio_tecnico_service.exception.ServicioTecnicoNotFoundException;
import com.automotora.servicio_tecnico_service.model.ServicioTecnico;
import com.automotora.servicio_tecnico_service.repository.ServicioTecnicoRepository;
import com.automotora.servicio_tecnico_service.service.ServicioTecnicoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ServicioTecnicoServiceTest {

    @Mock
    private ServicioTecnicoRepository repository;

    @Mock
    private VehiculoClient vehiculoClient;

    @InjectMocks
    private ServicioTecnicoService servicioTecnicoService;

    private ServicioTecnico servicio;
    private ServicioTecnicoRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        servicio = ServicioTecnico.builder()
                .id("uuid-servicio")
                .fechaIngreso(LocalDate.of(2024, 1, 10))
                .fechaSalida(LocalDate.of(2024, 1, 15))
                .diagnostico("Cambio de aceite y filtros")
                .costo(new BigDecimal("85000"))
                .estadoServicio("COMPLETADO")
                .vehiculoId("uuid-vehiculo")
                .build();

        requestDTO = new ServicioTecnicoRequestDTO();
        requestDTO.setFechaIngreso(LocalDate.of(2024, 1, 10));
        requestDTO.setFechaSalida(LocalDate.of(2024, 1, 15));
        requestDTO.setDiagnostico("Cambio de aceite y filtros");
        requestDTO.setCosto(new BigDecimal("85000"));
        requestDTO.setEstadoServicio("COMPLETADO");
        requestDTO.setVehiculoId("uuid-vehiculo");
    }

    // ---------- guardar ----------

    @Test
    void guardar_debeConsultarVehiculoYRetornarServicio() {
        // Given
        when(vehiculoClient.obtenerVehiculo("uuid-vehiculo")).thenReturn(null);
        when(repository.save(any(ServicioTecnico.class))).thenReturn(servicio);

        // When
        ServicioTecnicoResponseDTO resultado = servicioTecnicoService.guardar(requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("Cambio de aceite y filtros", resultado.getDiagnostico());
        assertEquals("COMPLETADO", resultado.getEstadoServicio());
        verify(vehiculoClient, times(1)).obtenerVehiculo("uuid-vehiculo");
        verify(repository, times(1)).save(any(ServicioTecnico.class));
    }

    // ---------- obtenerTodos ----------

    @Test
    void obtenerTodos_debeRetornarListaDeServicios() {
        // Given
        when(repository.findAll()).thenReturn(List.of(servicio));

        // When
        List<ServicioTecnicoResponseDTO> resultado = servicioTecnicoService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("COMPLETADO", resultado.get(0).getEstadoServicio());
    }

    // ---------- obtenerPorId ----------

    @Test
    void obtenerPorId_cuandoExiste_debeRetornarServicio() {
        // Given
        when(repository.findById("uuid-servicio")).thenReturn(Optional.of(servicio));

        // When
        ServicioTecnicoResponseDTO resultado = servicioTecnicoService.obtenerPorId("uuid-servicio");

        // Then
        assertNotNull(resultado);
        assertEquals("uuid-servicio", resultado.getId());
        assertEquals("Cambio de aceite y filtros", resultado.getDiagnostico());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(repository.findById("id-inexistente")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ServicioTecnicoNotFoundException.class,
                () -> servicioTecnicoService.obtenerPorId("id-inexistente"));
    }

    // ---------- actualizar ----------

    @Test
    void actualizar_cuandoExiste_debeRetornarServicioActualizado() {
        // Given
        ServicioTecnicoRequestDTO dtoActualizado = new ServicioTecnicoRequestDTO();
        dtoActualizado.setFechaIngreso(LocalDate.of(2024, 1, 10));
        dtoActualizado.setFechaSalida(LocalDate.of(2024, 1, 20));
        dtoActualizado.setDiagnostico("Revisión general");
        dtoActualizado.setCosto(new BigDecimal("120000"));
        dtoActualizado.setEstadoServicio("EN_PROCESO");
        dtoActualizado.setVehiculoId("uuid-vehiculo");

        ServicioTecnico servicioActualizado = ServicioTecnico.builder()
                .id("uuid-servicio")
                .diagnostico("Revisión general")
                .estadoServicio("EN_PROCESO")
                .vehiculoId("uuid-vehiculo")
                .build();

        when(repository.findById("uuid-servicio")).thenReturn(Optional.of(servicio));
        when(vehiculoClient.obtenerVehiculo("uuid-vehiculo")).thenReturn(null);
        when(repository.save(any(ServicioTecnico.class))).thenReturn(servicioActualizado);

        // When
        ServicioTecnicoResponseDTO resultado = servicioTecnicoService.actualizar("uuid-servicio", dtoActualizado);

        // Then
        assertNotNull(resultado);
        assertEquals("EN_PROCESO", resultado.getEstadoServicio());
        assertEquals("Revisión general", resultado.getDiagnostico());
    }

    @Test
    void actualizar_cuandoNoExiste_debeLanzarExcepcionYNoConsultarVehiculo() {
        // Given
        when(repository.findById("id-inexistente")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ServicioTecnicoNotFoundException.class,
                () -> servicioTecnicoService.actualizar("id-inexistente", requestDTO));

        // El servicio busca primero el registro; si no existe, no debería
        // ni siquiera consultar al vehiculo-service ni intentar guardar.
        verify(vehiculoClient, never()).obtenerVehiculo(any());
        verify(repository, never()).save(any());
    }

    // ---------- eliminar ----------

    @Test
    void eliminar_cuandoExiste_debeEliminarServicio() {
        // Given
        when(repository.findById("uuid-servicio")).thenReturn(Optional.of(servicio));
        doNothing().when(repository).delete(servicio);

        // When
        assertDoesNotThrow(() -> servicioTecnicoService.eliminar("uuid-servicio"));

        // Then
        verify(repository, times(1)).delete(servicio);
    }

    @Test
    void eliminar_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(repository.findById("id-inexistente")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ServicioTecnicoNotFoundException.class,
                () -> servicioTecnicoService.eliminar("id-inexistente"));
        verify(repository, never()).delete(any());
    }
}