package com.automotora.reserva_service.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.automotora.reserva_service.dto.request.ReservaRequestDTO;
import com.automotora.reserva_service.dto.response.ReservaResponseDTO;
import com.automotora.reserva_service.service.ReservaService;

import java.util.List;

@RestController
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
public class ReservaController {

    private final ReservaService reservaService;

    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> obtenerTodas() {

        return ResponseEntity.ok(
                reservaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(
                reservaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ReservaResponseDTO> guardar(
            @Valid @RequestBody ReservaRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservaService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody ReservaRequestDTO dto) {

        return ResponseEntity.ok(
                reservaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {

        reservaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}