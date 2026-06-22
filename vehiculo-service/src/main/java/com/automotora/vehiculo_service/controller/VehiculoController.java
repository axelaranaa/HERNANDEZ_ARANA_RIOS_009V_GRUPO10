package com.automotora.vehiculo_service.controller;

import com.automotora.vehiculo_service.dto.request.VehiculoRequestDTO;
import com.automotora.vehiculo_service.dto.response.VehiculoResponseDTO;
import com.automotora.vehiculo_service.service.VehiculoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/vehiculos")
@RequiredArgsConstructor
@Tag(name = "Vehículos", description = "Operaciones para gestionar vehículos de la automotora")
public class VehiculoController {

    private final VehiculoService vehiculoService;

    @Operation(summary = "Obtener todos los vehículos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de vehículos obtenida exitosamente",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    [
                      {
                        "id": "uuid-123",
                        "patente": "ABCD12",
                        "anio": 2022,
                        "kilometraje": 15000,
                        "precio": 12500000,
                        "color": "Blanco",
                        "transmision": "AUTOMATICA",
                        "combustible": "BENCINA",
                        "estado": "DISPONIBLE",
                        "modeloId": "uuid-modelo"
                      }
                    ]
                """)))
    })
    @GetMapping
    public ResponseEntity<List<VehiculoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(vehiculoService.obtenerTodos());
    }

    @Operation(summary = "Obtener vehículo por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehículo encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = VehiculoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "error": "Vehículo no encontrado con id: uuid-123" }
                """)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> obtenerPorId(
            @PathVariable String id) {
        return ResponseEntity.ok(vehiculoService.obtenerPorId(id));
    }

    @Operation(summary = "Crear un nuevo vehículo")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Vehículo creado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = VehiculoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "patente": "La patente es obligatoria" }
                """))),
        @ApiResponse(responseCode = "404", description = "Modelo no encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "error": "Modelo no encontrado con id: uuid-modelo" }
                """)))
    })
    @PostMapping
    public ResponseEntity<VehiculoResponseDTO> guardar(
            @Valid @RequestBody VehiculoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(vehiculoService.guardar(dto));
    }

    @Operation(summary = "Actualizar un vehículo existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Vehículo actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<VehiculoResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody VehiculoRequestDTO dto) {
        return ResponseEntity.ok(vehiculoService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar un vehículo")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Vehículo eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        vehiculoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}