package com.automotora.servicio_tecnico_service.controller;

import com.automotora.servicio_tecnico_service.dto.request.ServicioTecnicoRequestDTO;
import com.automotora.servicio_tecnico_service.dto.response.ServicioTecnicoResponseDTO;
import com.automotora.servicio_tecnico_service.service.ServicioTecnicoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/servicios-tecnicos")
@RequiredArgsConstructor
public class ServicioTecnicoController {

    private final ServicioTecnicoService servicioTecnicoService;

    @GetMapping
    public ResponseEntity<List<ServicioTecnicoResponseDTO>> obtenerTodos() {

        return ResponseEntity.ok(
                servicioTecnicoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServicioTecnicoResponseDTO> obtenerPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(
                servicioTecnicoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ServicioTecnicoResponseDTO> guardar(
            @Valid @RequestBody ServicioTecnicoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicioTecnicoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioTecnicoResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody ServicioTecnicoRequestDTO dto) {

        return ResponseEntity.ok(
                servicioTecnicoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {

        servicioTecnicoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}