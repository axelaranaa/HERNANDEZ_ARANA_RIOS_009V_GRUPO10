package com.automotora.modelo_service.controller;

import com.automotora.modelo_service.dto.request.ModeloRequestDTO;
import com.automotora.modelo_service.dto.response.ModeloResponseDTO;
import com.automotora.modelo_service.service.ModeloService;
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
@RequestMapping("/api/modelos")
@RequiredArgsConstructor
@Tag(name = "Modelos", description = "Operaciones para gestionar modelos de vehículos")
public class ModeloController {

    private final ModeloService modeloService;

    @Operation(summary = "Obtener todos los modelos")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de modelos obtenida exitosamente",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    [
                      {
                        "id": "uuid-123",
                        "nombreModelo": "Corolla",
                        "tipoVehiculo": "Sedán",
                        "marcaId": "uuid-marca"
                      }
                    ]
                """)))
    })
    @GetMapping
    public ResponseEntity<List<ModeloResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(modeloService.obtenerTodos());
    }

    @Operation(summary = "Obtener modelo por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Modelo encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ModeloResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Modelo no encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "error": "Modelo no encontrado" }
                """)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ModeloResponseDTO> obtenerPorId(
            @PathVariable String id) {
        return ResponseEntity.ok(modeloService.obtenerPorId(id));
    }

    @Operation(summary = "Crear un nuevo modelo")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Modelo creado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ModeloResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "nombreModelo": "El nombre del modelo es obligatorio" }
                """)))
    })
    @PostMapping
    public ResponseEntity<ModeloResponseDTO> guardar(
            @Valid @RequestBody ModeloRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(modeloService.guardar(dto));
    }

    @Operation(summary = "Actualizar un modelo existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Modelo actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Modelo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ModeloResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody ModeloRequestDTO dto) {
        return ResponseEntity.ok(modeloService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar un modelo")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Modelo eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Modelo no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        modeloService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}