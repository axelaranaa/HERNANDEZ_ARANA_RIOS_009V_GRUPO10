package com.automotora.financiamiento_service.controller;

import com.automotora.financiamiento_service.dto.request.FinanciamientoRequestDTO;
import com.automotora.financiamiento_service.dto.response.FinanciamientoResponseDTO;
import com.automotora.financiamiento_service.service.FinanciamientoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/financiamientos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Financiamiento", description = "Endpoints para la evaluación y otorgamiento de créditos automotrices")
public class FinanciamientoController {

    private final FinanciamientoService financiamientoService;

    @GetMapping
    @Operation(summary = "Obtener todas las solicitudes de financiamiento")
    public ResponseEntity<List<FinanciamientoResponseDTO>> obtenerTodos() {
        log.info("REST: Petición para listar financiamientos");
        return ResponseEntity.ok(financiamientoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener detalles de un financiamiento por ID")
    public ResponseEntity<FinanciamientoResponseDTO> obtenerPorId(@PathVariable String id) {
        log.info("REST: Buscando financiamiento ID: {}", id);
        return ResponseEntity.ok(financiamientoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva solicitud de financiamiento")
    public ResponseEntity<FinanciamientoResponseDTO> crear(@Valid @RequestBody FinanciamientoRequestDTO dto) {
        log.info("REST: Solicitando nuevo financiamiento");
        return ResponseEntity.status(HttpStatus.CREATED).body(financiamientoService.crear(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar una solicitud de financiamiento existente")
    public ResponseEntity<FinanciamientoResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody FinanciamientoRequestDTO dto) {
        log.info("REST: Actualizando financiamiento ID: {}", id);
        return ResponseEntity.ok(financiamientoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar el registro de un financiamiento")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        log.info("REST: Eliminando financiamiento ID: {}", id);
        financiamientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}