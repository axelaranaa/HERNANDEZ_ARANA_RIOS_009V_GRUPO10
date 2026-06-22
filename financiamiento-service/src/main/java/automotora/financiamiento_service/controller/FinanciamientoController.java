package automotora.financiamiento_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid; // <--- Crucial para activar los @NotNull del DTO
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import automotora.financiamiento_service.dto.request.FinanciamientoRequestDTO;
import automotora.financiamiento_service.dto.response.FinanciamientoResponseDTO;
import automotora.financiamiento_service.service.FinanciamientoService;

import java.util.List;

@RestController
@RequestMapping("v1/financiamientos") // <--- Modificado para que calce exacto con el API Gateway
@RequiredArgsConstructor
@Tag(name = "Financiamiento", description = "Endpoints para la gestión de solicitudes de financiamientos")
public class FinanciamientoController {

    private final FinanciamientoService financiamientoService;

    @GetMapping
    @Operation(summary = "Obtener todos los financiamientos registrados")
    public ResponseEntity<List<FinanciamientoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(
                financiamientoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un financiamiento específico por su ID")
    public ResponseEntity<FinanciamientoResponseDTO> obtenerPorId(
            @PathVariable String id) {
        return ResponseEntity.ok(
                financiamientoService.obtenerPorId(id));
    }

    @PostMapping
    @Operation(summary = "Crear una nueva solicitud de financiamiento")
    public ResponseEntity<FinanciamientoResponseDTO> guardar(
            @Valid @RequestBody FinanciamientoRequestDTO dto) { // <--- Se agrega @Valid
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(financiamientoService.guardar(dto));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un financiamiento existente")
    public ResponseEntity<FinanciamientoResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody FinanciamientoRequestDTO dto) { // <--- Se agrega @Valid
        return ResponseEntity.ok(
                financiamientoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un registro de financiamiento")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {
        financiamientoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}