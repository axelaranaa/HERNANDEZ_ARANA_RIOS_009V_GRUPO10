package com.automotora.servicio_tecnico_service.controller;

import com.automotora.servicio_tecnico_service.dto.request.ServicioTecnicoRequestDTO;
import com.automotora.servicio_tecnico_service.dto.response.ServicioTecnicoResponseDTO;
import com.automotora.servicio_tecnico_service.service.ServicioTecnicoService;
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
@RequestMapping("/api/servicios-tecnicos")
@RequiredArgsConstructor
@Tag(name = "Servicios Técnicos", description = "Operaciones para gestionar servicios técnicos de vehículos")
public class ServicioTecnicoController {

    private final ServicioTecnicoService servicioTecnicoService;

    @Operation(summary = "Obtener todos los servicios técnicos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista obtenida exitosamente",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    [
                      {
                        "id": "uuid-123",
                        "fechaIngreso": "2024-01-10",
                        "fechaSalida": "2024-01-15",
                        "diagnostico": "Cambio de aceite y filtros",
                        "costo": 85000,
                        "estadoServicio": "COMPLETADO",
                        "vehiculoId": "uuid-vehiculo"
                      }
                    ]
                """)))
    })
    @GetMapping
    public ResponseEntity<List<ServicioTecnicoResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(servicioTecnicoService.obtenerTodos());
    }

    @Operation(summary = "Obtener servicio técnico por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servicio técnico encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ServicioTecnicoResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Servicio técnico no encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "error": "Servicio técnico no encontrado con id: uuid-123" }
                """)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ServicioTecnicoResponseDTO> obtenerPorId(
            @PathVariable String id) {
        return ResponseEntity.ok(servicioTecnicoService.obtenerPorId(id));
    }

    @Operation(summary = "Crear un nuevo servicio técnico")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Servicio técnico creado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ServicioTecnicoResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "vehiculoId": "El ID del vehículo es obligatorio" }
                """))),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "error": "Vehículo no encontrado con id: uuid-vehiculo" }
                """)))
    })
    @PostMapping
    public ResponseEntity<ServicioTecnicoResponseDTO> guardar(
            @Valid @RequestBody ServicioTecnicoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(servicioTecnicoService.guardar(dto));
    }

    @Operation(summary = "Actualizar un servicio técnico existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Servicio técnico actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Servicio técnico no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ServicioTecnicoResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody ServicioTecnicoRequestDTO dto) {
        return ResponseEntity.ok(servicioTecnicoService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar un servicio técnico")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Servicio técnico eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Servicio técnico no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        servicioTecnicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}