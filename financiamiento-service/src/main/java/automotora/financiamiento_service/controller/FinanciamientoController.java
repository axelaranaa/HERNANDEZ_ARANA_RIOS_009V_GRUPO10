package automotora.financiamiento_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import automotora.financiamiento_service.dto.request.FinanciamientoRequestDTO;
import automotora.financiamiento_service.dto.response.FinanciamientoResponseDTO;
import automotora.financiamiento_service.service.FinanciamientoService;

import java.util.List;

@RestController
@RequestMapping("/api/financiamientos")
@RequiredArgsConstructor
public class FinanciamientoController {

    private final FinanciamientoService financiamientoService;

    @GetMapping
    public ResponseEntity<List<FinanciamientoResponseDTO>> obtenerTodos() {

        return ResponseEntity.ok(
                financiamientoService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<FinanciamientoResponseDTO> obtenerPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(
                financiamientoService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<FinanciamientoResponseDTO> guardar(
            @RequestBody FinanciamientoRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(financiamientoService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FinanciamientoResponseDTO> actualizar(
            @PathVariable String id,
            @RequestBody FinanciamientoRequestDTO dto) {

        return ResponseEntity.ok(
                financiamientoService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {

        financiamientoService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}