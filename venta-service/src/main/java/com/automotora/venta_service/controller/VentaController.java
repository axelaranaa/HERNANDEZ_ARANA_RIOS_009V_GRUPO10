package com.automotora.venta_service.controller;

import com.automotora.venta_service.dto.request.VentaRequestDTO;
import com.automotora.venta_service.dto.response.VentaResponseDTO;
import com.automotora.venta_service.service.VentaService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
@Slf4j
public class VentaController {

    private final VentaService VentaService;

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> obtenerTodasLasVentas() {
        log.info("REST: Petición para listar todas las ventas");
        return ResponseEntity.ok(VentaService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> obtenerVentaPorId(@PathVariable String id) {
        log.info("REST: Petición para buscar la venta con ID: {}", id);
        return ResponseEntity.ok(VentaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<VentaResponseDTO> registrarVenta(@Valid @RequestBody VentaRequestDTO requestDTO) {
        log.info("REST: Petición para registrar una nueva venta");
        VentaResponseDTO nuevaVenta = VentaService.guardar(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaVenta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarVenta(@PathVariable String id) {
        log.info("REST: Petición para eliminar la venta con ID: {}", id);
        VentaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}