package com.automotora.modelo_service;

import com.automotora.modelo_service.client.MarcaClient;
import com.automotora.modelo_service.dto.request.ModeloRequestDTO;
import com.automotora.modelo_service.dto.response.MarcaResponseDTO;
import com.automotora.modelo_service.dto.response.ModeloResponseDTO;
import com.automotora.modelo_service.exception.ModeloNotFoundException;
import com.automotora.modelo_service.exception.RecursoRelacionadoNoEncontradoException;
import com.automotora.modelo_service.exception.ServicioExternoNoDisponibleException;
import com.automotora.modelo_service.model.Modelo;
import com.automotora.modelo_service.repository.ModeloRepository;
import com.automotora.modelo_service.service.ModeloService;

import feign.FeignException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ModeloServiceTest {

    @Mock
    private ModeloRepository modeloRepository;

    @Mock
    private MarcaClient marcaClient;

    @InjectMocks
    private ModeloService modeloService;

    private Modelo modelo;
    private ModeloRequestDTO requestDTO;
    private MarcaResponseDTO marcaValida;

    @BeforeEach
    void setUp() {
        modelo = Modelo.builder()
                .id("uuid-modelo")
                .nombreModelo("Corolla")
                .tipoVehiculo("Sedan")
                .marcaId("uuid-marca")
                .build();

        requestDTO = new ModeloRequestDTO();
        requestDTO.setNombreModelo("Corolla");
        requestDTO.setTipoVehiculo("Sedan");
        requestDTO.setMarcaId("uuid-marca");

        marcaValida = new MarcaResponseDTO("uuid-marca", "Toyota");
    }

    // ---------- guardar ----------

    @Test
    void guardar_conTipoValido_debeCrearModelo() {
        // Given
        when(marcaClient.obtenerMarcaPorId("uuid-marca")).thenReturn(marcaValida);
        when(modeloRepository.existsByNombreModeloAndMarcaId("Corolla", "uuid-marca"))
                .thenReturn(false);
        when(modeloRepository.save(any(Modelo.class))).thenReturn(modelo);

        // When
        ModeloResponseDTO resultado = modeloService.guardar(requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("Corolla", resultado.getNombreModelo());
        assertEquals("Sedan", resultado.getTipoVehiculo());
        verify(marcaClient, times(1)).obtenerMarcaPorId("uuid-marca");
        verify(modeloRepository, times(1)).save(any(Modelo.class));
    }

    @Test
    void guardar_conTipoInvalido_debeLanzarExcepcion() {
        // Given
        when(marcaClient.obtenerMarcaPorId("uuid-marca")).thenReturn(marcaValida);
        requestDTO.setTipoVehiculo("Moto");

        // When / Then
        assertThrows(RuntimeException.class,
                () -> modeloService.guardar(requestDTO));
        verify(modeloRepository, never()).save(any());
    }

    @Test
    void guardar_cuandoModeloDuplicado_debeLanzarExcepcion() {
        // Given
        when(marcaClient.obtenerMarcaPorId("uuid-marca")).thenReturn(marcaValida);
        when(modeloRepository.existsByNombreModeloAndMarcaId("Corolla", "uuid-marca"))
                .thenReturn(true);

        // When / Then
        assertThrows(RuntimeException.class,
                () -> modeloService.guardar(requestDTO));
        verify(modeloRepository, never()).save(any());
    }

    @Test
    void guardar_cuandoMarcaNoExiste_debeLanzarExcepcion() {
        // Given
        // OJO: no usar .thenThrow(FeignException.NotFound.class) -> Mockito necesita
        // un constructor vacío para instanciarla y FeignException no lo tiene.
        // Se usa mock() para crear una instancia simulada en vez de instanciarla de verdad.
        when(marcaClient.obtenerMarcaPorId("uuid-marca"))
                .thenThrow(mock(FeignException.NotFound.class));

        // When / Then
        assertThrows(RecursoRelacionadoNoEncontradoException.class,
                () -> modeloService.guardar(requestDTO));
        verify(modeloRepository, never()).save(any());
    }

    @Test
    void guardar_cuandoMarcaServiceNoDisponible_debeLanzarExcepcion() {
        // Given
        when(marcaClient.obtenerMarcaPorId("uuid-marca"))
                .thenThrow(mock(FeignException.ServiceUnavailable.class));

        // When / Then
        assertThrows(ServicioExternoNoDisponibleException.class,
                () -> modeloService.guardar(requestDTO));
        verify(modeloRepository, never()).save(any());
    }

    @Test
    void guardar_cuandoMarcaEsNula_debeLanzarExcepcion() {
        // Given: el cliente responde 200 pero con cuerpo vacío/null
        when(marcaClient.obtenerMarcaPorId("uuid-marca")).thenReturn(null);

        // When / Then
        assertThrows(RecursoRelacionadoNoEncontradoException.class,
                () -> modeloService.guardar(requestDTO));
        verify(modeloRepository, never()).save(any());
    }

    // ---------- obtenerTodos ----------

    @Test
    void obtenerTodos_debeRetornarListaDeModelos() {
        // Given
        when(modeloRepository.findAll()).thenReturn(List.of(modelo));

        // When
        List<ModeloResponseDTO> resultado = modeloService.obtenerTodos();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Corolla", resultado.get(0).getNombreModelo());
    }

    // ---------- obtenerPorId ----------

    @Test
    void obtenerPorId_cuandoExiste_debeRetornarModelo() {
        // Given
        when(modeloRepository.findById("uuid-modelo")).thenReturn(Optional.of(modelo));

        // When
        ModeloResponseDTO resultado = modeloService.obtenerPorId("uuid-modelo");

        // Then
        assertNotNull(resultado);
        assertEquals("uuid-modelo", resultado.getId());
        assertEquals("Corolla", resultado.getNombreModelo());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(modeloRepository.findById("id-inexistente")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ModeloNotFoundException.class,
                () -> modeloService.obtenerPorId("id-inexistente"));
    }

    // ---------- actualizar ----------

    @Test
    void actualizar_cuandoExiste_debeRetornarModeloActualizado() {
        // Given
        ModeloRequestDTO dtoActualizado = new ModeloRequestDTO();
        dtoActualizado.setNombreModelo("Corolla Cross");
        dtoActualizado.setTipoVehiculo("SUV");
        dtoActualizado.setMarcaId("uuid-marca");

        Modelo modeloActualizado = Modelo.builder()
                .id("uuid-modelo")
                .nombreModelo("Corolla Cross")
                .tipoVehiculo("SUV")
                .marcaId("uuid-marca")
                .build();

        when(modeloRepository.findById("uuid-modelo")).thenReturn(Optional.of(modelo));
        when(marcaClient.obtenerMarcaPorId("uuid-marca")).thenReturn(marcaValida);
        when(modeloRepository.save(any(Modelo.class))).thenReturn(modeloActualizado);

        // When
        ModeloResponseDTO resultado = modeloService.actualizar("uuid-modelo", dtoActualizado);

        // Then
        assertNotNull(resultado);
        assertEquals("Corolla Cross", resultado.getNombreModelo());
        assertEquals("SUV", resultado.getTipoVehiculo());
    }

    @Test
    void actualizar_cuandoNoExiste_debeLanzarExcepcionYNoValidarMarca() {
        // Given
        when(modeloRepository.findById("id-inexistente")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ModeloNotFoundException.class,
                () -> modeloService.actualizar("id-inexistente", requestDTO));

        // El service busca el modelo ANTES de validar la marca
        verify(marcaClient, never()).obtenerMarcaPorId(any());
        verify(modeloRepository, never()).save(any());
    }

    @Test
    void actualizar_cuandoMarcaNoExiste_debeLanzarExcepcionYNoGuardar() {
        // Given
        when(modeloRepository.findById("uuid-modelo")).thenReturn(Optional.of(modelo));
        when(marcaClient.obtenerMarcaPorId("uuid-marca")).thenReturn(null);

        // When / Then
        assertThrows(RecursoRelacionadoNoEncontradoException.class,
                () -> modeloService.actualizar("uuid-modelo", requestDTO));
        verify(modeloRepository, never()).save(any());
    }

    @Test
    void actualizar_conTipoInvalido_debeLanzarExcepcionYNoGuardar() {
        // Given
        requestDTO.setTipoVehiculo("Moto");
        when(modeloRepository.findById("uuid-modelo")).thenReturn(Optional.of(modelo));
        when(marcaClient.obtenerMarcaPorId("uuid-marca")).thenReturn(marcaValida);

        // When / Then
        assertThrows(RuntimeException.class,
                () -> modeloService.actualizar("uuid-modelo", requestDTO));
        verify(modeloRepository, never()).save(any());
    }

    // ---------- eliminar ----------

    @Test
    void eliminar_cuandoExiste_debeEliminarModelo() {
        // Given
        when(modeloRepository.findById("uuid-modelo")).thenReturn(Optional.of(modelo));
        doNothing().when(modeloRepository).delete(modelo);

        // When
        assertDoesNotThrow(() -> modeloService.eliminar("uuid-modelo"));

        // Then
        verify(modeloRepository, times(1)).delete(modelo);
    }

    @Test
    void eliminar_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(modeloRepository.findById("id-inexistente")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(ModeloNotFoundException.class,
                () -> modeloService.eliminar("id-inexistente"));
        verify(modeloRepository, never()).delete(any());
    }
}