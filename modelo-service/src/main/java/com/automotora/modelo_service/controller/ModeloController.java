package com.automotora.modelo_service.controller;

import com.automotora.modelo_service.dto.request.ModeloRequestDTO;
import com.automotora.modelo_service.dto.response.ModeloResponseDTO;
import com.automotora.modelo_service.service.ModeloService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/modelos")
@RequiredArgsConstructor
public class ModeloController {

    private final ModeloService modeloService;

    @GetMapping
    public ResponseEntity<List<ModeloResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(modeloService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ModeloResponseDTO> obtenerPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(modeloService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ModeloResponseDTO> guardar(
            @Valid @RequestBody ModeloRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modeloService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ModeloResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody ModeloRequestDTO dto) {

        return ResponseEntity.ok(
                modeloService.actualizar(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {

        modeloService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}