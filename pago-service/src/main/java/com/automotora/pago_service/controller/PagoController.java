package com.automotora.pago_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.automotora.pago_service.dto.request.PagoRequestDTO;
import com.automotora.pago_service.dto.response.PagoResponseDTO;
import com.automotora.pago_service.service.PagoService;

import java.util.List;

@RestController
@RequestMapping("/api/pagos") // Ruta limpia para mapear en el API Gateway
@RequiredArgsConstructor
@Tag(name = "Pago", description = "Endpoints para la gestión, registro y control de pagos de ventas")
public class PagoController {

    // CORREGIDO: Variable en minúscula para evitar los más de 9 errores de compilación
    private final PagoService pagoService;

    @GetMapping
    @Operation(summary = "Obtener todos los pagos registrados")
    public ResponseEntity<List<PagoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(pagoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener los detalles de un pago específico por su ID")
    public ResponseEntity<PagoResponseDTO> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(pagoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Registrar un nuevo pago asociado a una venta")
    public ResponseEntity<PagoResponseDTO> crear(@Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(pagoService.crear(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar la información de un pago existente")
    public ResponseEntity<PagoResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody PagoRequestDTO dto) {
        return ResponseEntity.ok(pagoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar el registro de un pago")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
