package com.automotora.marca_service;

import com.automotora.marca_service.dto.request.MarcaRequestDTO;
import com.automotora.marca_service.dto.response.MarcaResponseDTO;
import com.automotora.marca_service.exception.MarcaNotFoundException;
import com.automotora.marca_service.model.Marca;
import com.automotora.marca_service.repository.MarcaRepository;
import com.automotora.marca_service.service.MarcaService;

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
class MarcaServiceTest {

    @Mock
    private MarcaRepository marcaRepository;

    @InjectMocks
    private MarcaService marcaService;

    private Marca marca;
    private MarcaRequestDTO requestDTO;

    @BeforeEach
    void setUp() {
        marca = Marca.builder()
                .id("uuid-123")
                .nombreMarca("Toyota")
                .build();

        requestDTO = new MarcaRequestDTO();
        requestDTO.setNombreMarca("Toyota");
    }

    // ---------- guardar ----------

    @Test
    void guardar_debeRetornarMarcaCreada() {
        // Given
        when(marcaRepository.save(any(Marca.class))).thenReturn(marca);

        // When
        MarcaResponseDTO resultado = marcaService.guardar(requestDTO);

        // Then
        assertNotNull(resultado);
        assertEquals("Toyota", resultado.getNombreMarca());
        verify(marcaRepository, times(1)).save(any(Marca.class));
    }

    // ---------- obtenerTodas ----------

    @Test
    void obtenerTodas_debeRetornarListaDeMarcas() {
        // Given
        when(marcaRepository.findAll()).thenReturn(List.of(marca));

        // When
        List<MarcaResponseDTO> resultado = marcaService.obtenerTodas();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Toyota", resultado.get(0).getNombreMarca());
        verify(marcaRepository, times(1)).findAll();
    }

    // ---------- obtenerPorId ----------

    @Test
    void obtenerPorId_cuandoExiste_debeRetornarMarca() {
        // Given
        when(marcaRepository.findById("uuid-123")).thenReturn(Optional.of(marca));

        // When
        MarcaResponseDTO resultado = marcaService.obtenerPorId("uuid-123");

        // Then
        assertNotNull(resultado);
        assertEquals("uuid-123", resultado.getId());
        assertEquals("Toyota", resultado.getNombreMarca());
    }

    @Test
    void obtenerPorId_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(marcaRepository.findById("id-inexistente")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(MarcaNotFoundException.class,
                () -> marcaService.obtenerPorId("id-inexistente"));
    }

    // ---------- actualizar ----------

    @Test
    void actualizar_cuandoExiste_debeRetornarMarcaActualizada() {
        // Given
        MarcaRequestDTO dtoActualizado = new MarcaRequestDTO();
        dtoActualizado.setNombreMarca("Honda");

        Marca marcaActualizada = Marca.builder()
                .id("uuid-123")
                .nombreMarca("Honda")
                .build();

        when(marcaRepository.findById("uuid-123")).thenReturn(Optional.of(marca));
        when(marcaRepository.save(any(Marca.class))).thenReturn(marcaActualizada);

        // When
        MarcaResponseDTO resultado = marcaService.actualizar("uuid-123", dtoActualizado);

        // Then
        assertNotNull(resultado);
        assertEquals("Honda", resultado.getNombreMarca());
    }

    @Test
    void actualizar_cuandoNoExiste_debeLanzarExcepcionYNoGuardar() {
        // Given
        when(marcaRepository.findById("id-inexistente")).thenReturn(Optional.empty());

        MarcaRequestDTO dto = new MarcaRequestDTO();
        dto.setNombreMarca("Honda");

        // When / Then
        assertThrows(MarcaNotFoundException.class,
                () -> marcaService.actualizar("id-inexistente", dto));
        verify(marcaRepository, never()).save(any());
    }

    // ---------- eliminar ----------

    @Test
    void eliminar_cuandoExiste_debeEliminarMarca() {
        // Given
        when(marcaRepository.findById("uuid-123")).thenReturn(Optional.of(marca));
        doNothing().when(marcaRepository).delete(marca);

        // When
        assertDoesNotThrow(() -> marcaService.eliminar("uuid-123"));

        // Then
        verify(marcaRepository, times(1)).delete(marca);
    }

    @Test
    void eliminar_cuandoNoExiste_debeLanzarExcepcion() {
        // Given
        when(marcaRepository.findById("id-inexistente")).thenReturn(Optional.empty());

        // When / Then
        assertThrows(MarcaNotFoundException.class,
                () -> marcaService.eliminar("id-inexistente"));
        verify(marcaRepository, never()).delete(any());
    }
}