package com.automotora.marca_service.controller;

import com.automotora.marca_service.dto.request.MarcaRequestDTO;
import com.automotora.marca_service.dto.response.MarcaResponseDTO;
import com.automotora.marca_service.service.MarcaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
public class MarcaController {

    private final MarcaService marcaService;

    @GetMapping
    public ResponseEntity<List<MarcaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(marcaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponseDTO> obtenerPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(marcaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<MarcaResponseDTO> guardar(
            @Valid @RequestBody MarcaRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(marcaService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody MarcaRequestDTO dto) {

        return ResponseEntity.ok(
                marcaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {

        marcaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}