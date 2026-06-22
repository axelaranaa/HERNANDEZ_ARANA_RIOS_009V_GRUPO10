package com.automotora.vehiculo_service;

import com.automotora.vehiculo_service.client.ModeloClient;
import com.automotora.vehiculo_service.dto.request.VehiculoRequestDTO;
import com.automotora.vehiculo_service.dto.response.ModeloResponseDTO;
import com.automotora.vehiculo_service.dto.response.VehiculoResponseDTO;
import com.automotora.vehiculo_service.exception.RecursoRelacionadoNoEncontradoException;
import com.automotora.vehiculo_service.exception.ServicioExternoNoDisponibleException;
import com.automotora.vehiculo_service.exception.VehiculoNotFoundException;
import com.automotora.vehiculo_service.model.Vehiculo;
import com.automotora.vehiculo_service.repository.VehiculoRepository;
import com.automotora.vehiculo_service.service.VehiculoService;
import feign.FeignException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehiculoServiceTest {

    @Mock
    private VehiculoRepository vehiculoRepository;

    @Mock
    private ModeloClient modeloClient;

    @InjectMocks
    private VehiculoService vehiculoService;

    private Vehiculo vehiculo;
    private VehiculoRequestDTO requestDTO;
    private ModeloResponseDTO modeloExistente;

    @BeforeEach
    void setUp() {
        vehiculo = Vehiculo.builder()
                .id("uuid-vehiculo")
                .patente("ABCD12")
                .anio(2022)
                .kilometraje(new BigDecimal("15000"))
                .precio(new BigDecimal("12500000"))
                .color("Blanco")
                .transmision("AUTOMATICA")
                .combustible("BENCINA")
                .estado("DISPONIBLE")
                .modeloId("uuid-modelo")
                .build();

        requestDTO = new VehiculoRequestDTO();
        requestDTO.setPatente("ABCD12");
        requestDTO.setAnio(2022);
        requestDTO.setKilometraje(new BigDecimal("15000"));
        requestDTO.setPrecio(new BigDecimal("12500000"));
        requestDTO.setColor("Blanco");
        requestDTO.setTransmision("AUTOMATICA");
        requestDTO.setCombustible("BENCINA");
        requestDTO.setEstado("DISPONIBLE");
        requestDTO.setModeloId("uuid-modelo");

        // Modelo "existente" genérico: el service solo necesita que no sea null,
        // no le interesan sus campos internos.
        modeloExistente = mock(ModeloResponseDTO.class);
    }

    // ---------- guardar ----------

    @Test
    void guardar_debeValidarModeloYRetornarVehiculo() {
        // Given
        when(modeloClient.obtenerModelo("uuid-modelo")).thenReturn(modeloExistente);
        when(vehiculoRepository.save(any(Vehiculo.class))).thenReturn(vehiculo);

        // When
        VehiculoResponseDTO resultado = vehiculoService.guardar(requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("ABCD12", resultado.getPatente());
        assertEquals("DISPONIBLE", resultado.getEstado());
        verify(modeloClient, times(1)).obtenerModelo("uuid-modelo");
        verify(vehiculoRepository, times(1)).save(any(Vehiculo.class));
    }

    @Test
    void guardar_cuandoModeloEsNulo_debeLanzarExcepcionYNoGuardar() {
        // Given
        when(modeloClient.obtenerModelo("uuid-modelo")).thenReturn(null);

        // When / Then
        assertThrows(RecursoRelacionadoNoEncontradoException.class,
                () -> vehiculoService.guardar(requestDTO));
        verify(vehiculoRepository, never()).save(any());
    }

    @Test
    void guardar_cuandoModeloClientRetornaNotFound_debeLanzarExcepcionDeRecursoRelacionado() {
        // Given
        when(modeloClient.obtenerModelo("uuid-modelo"))
                .thenThrow(mock(FeignException.NotFound.class));

        // When / Then
        assertThrows(RecursoRelacionadoNoEncontradoException.class,
                () -> vehiculoService.guardar(requestDTO));
        verify(vehiculoRepository, never()).save(any());
    }

    @Test
    void guardar_cuandoModeloServiceNoResponde_debeLanzarServicioExternoNoDisponible() {
        // Given: cualquier FeignException distinta de NotFound (timeout, 500, etc.)
        when(modeloClient.obtenerModelo("uuid-modelo"))
                .thenThrow(mock(FeignException.class));

        // When / Then
        assertThrows(ServicioExternoNoDisponibleException.class,
                () -> vehiculoService.guardar(requestDTO));
        verify(vehiculoRepository, never()).save(any());
    }

    // ---------- obtenerTodos ----------

    @Test
    void obtenerTodos_debeRetornarListaDeVehiculos() {
        // Given
        when(vehiculoRepository.findAll()).thenReturn(List.of(vehiculo));

        // When
        List<VehiculoResponseDTO> resultado = vehiculoService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("ABCD12", resultado.get(0).getPatente());
    }

    // ---------- obtenerPorId ----------

    @Test
    void obtenerPorId_cuandoExiste_debeRetornarVehiculo() {
        // Given
        when(vehiculoRepository.findById("uuid-vehiculo"))
                .thenReturn(Optional.of(vehiculo));

        // When
        VehiculoResponseDTO resultado = vehiculoService.obtenerPorId("uuid-vehiculo");

        // Then
        assertNotNull(resultado);
        assertEquals("uuid-vehiculo", resultado.getId());
        assertEquals("ABCD12", resultado.getPatente());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(vehiculoRepository.findById("id-inexistente"))
                .thenReturn(Optional.empty());

        // When / Then
        assertThrows(VehiculoNotFoundException.class,
                () -> vehiculoService.obtenerPorId("id-inexistente"));
    }

    // ---------- actualizar ----------

    @Test
    void actualizar_cuandoExiste_debeRetornarVehiculoActualizado() {
        // Given
        requestDTO.setEstado("VENDIDO");
        requestDTO.setColor("Negro");

        Vehiculo vehiculoActualizado = Vehiculo.builder()
                .id("uuid-vehiculo")
                .patente("ABCD12")
                .anio(2022)
                .kilometraje(new BigDecimal("15000"))
                .precio(new BigDecimal("12500000"))
                .color("Negro")
                .transmision("AUTOMATICA")
                .combustible("BENCINA")
                .estado("VENDIDO")
                .modeloId("uuid-modelo")
                .build();

        when(vehiculoRepository.findById("uuid-vehiculo"))
                .thenReturn(Optional.of(vehiculo));
        when(modeloClient.obtenerModelo("uuid-modelo")).thenReturn(modeloExistente);
        when(vehiculoRepository.save(any(Vehiculo.class)))
                .thenReturn(vehiculoActualizado);

        // When
        VehiculoResponseDTO resultado = vehiculoService.actualizar("uuid-vehiculo", requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("VENDIDO", resultado.getEstado());
        assertEquals("Negro", resultado.getColor());
    }

    @Test
    void actualizar_cuandoVehiculoNoExiste_debeLanzarExcepcionYNoValidarModelo() {
        // Given
        when(vehiculoRepository.findById("id-inexistente"))
                .thenReturn(Optional.empty());

        // When / Then
        assertThrows(VehiculoNotFoundException.class,
                () -> vehiculoService.actualizar("id-inexistente", requestDTO));

        // El service busca el vehículo ANTES de validar el modelo,
        // por lo tanto no debería ni siquiera llamar al modeloClient.
        verify(modeloClient, never()).obtenerModelo(any());
        verify(vehiculoRepository, never()).save(any());
    }

    @Test
    void actualizar_cuandoModeloNoExiste_debeLanzarExcepcionYNoGuardarCambios() {
        // Given
        when(vehiculoRepository.findById("uuid-vehiculo"))
                .thenReturn(Optional.of(vehiculo));
        when(modeloClient.obtenerModelo("uuid-modelo")).thenReturn(null);

        // When / Then
        assertThrows(RecursoRelacionadoNoEncontradoException.class,
                () -> vehiculoService.actualizar("uuid-vehiculo", requestDTO));
        verify(vehiculoRepository, never()).save(any());
    }

    // ---------- eliminar ----------

    @Test
    void eliminar_cuandoExiste_debeEliminarVehiculo() {
        // Given
        when(vehiculoRepository.findById("uuid-vehiculo"))
                .thenReturn(Optional.of(vehiculo));
        doNothing().when(vehiculoRepository).delete(vehiculo);

        // When
        assertDoesNotThrow(() -> vehiculoService.eliminar("uuid-vehiculo"));

        // Then
        verify(vehiculoRepository, times(1)).delete(vehiculo);
    }

    @Test
    void eliminar_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(vehiculoRepository.findById("id-inexistente"))
                .thenReturn(Optional.empty());

        // When / Then
        assertThrows(VehiculoNotFoundException.class,
                () -> vehiculoService.eliminar("id-inexistente"));
        verify(vehiculoRepository, never()).delete(any());
    }
}