package automotora.venta_service.controller;

import automotora.venta_service.dto.request.VentaRequestDTO;
import automotora.venta_service.dto.response.VentaResponseDTO;
import automotora.venta_service.service.VentaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final VentaService ventaService;

    @GetMapping
    public ResponseEntity<List<VentaResponseDTO>> obtenerTodas() {

        return ResponseEntity.ok(
                ventaService.obtenerTodas());
    }

    @GetMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> obtenerPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(
                ventaService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<VentaResponseDTO> guardar(
            @Valid @RequestBody VentaRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ventaService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<VentaResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody VentaRequestDTO dto) {

        return ResponseEntity.ok(
                ventaService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {

        ventaService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}