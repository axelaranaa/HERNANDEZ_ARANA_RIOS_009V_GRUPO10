package com.automotora.vehiculo_service.controller;

import com.automotora.vehiculo_service.dto.request.VehiculoRequestDTO;
import com.automotora.vehiculo_service.dto.response.VehiculoResponseDTO;
import com.automotora.vehiculo_service.service.VehiculoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @GetMapping
    public ResponseEntity<List<VehiculoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> obtenerPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(
                vehiculoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> guardar(
            @Valid @RequestBody VehiculoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehiculoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody VehiculoRequestDTO dto) {

        return ResponseEntity.ok(
                vehiculoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {

        vehiculoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}