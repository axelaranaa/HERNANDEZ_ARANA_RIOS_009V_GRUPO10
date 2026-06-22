package com.automotora.marca_service.controller;

import com.automotora.marca_service.dto.request.MarcaRequestDTO;
import com.automotora.marca_service.dto.response.MarcaResponseDTO;
import com.automotora.marca_service.service.MarcaService;
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
@RequestMapping("/api/marcas")
@RequiredArgsConstructor
@Tag(name = "Marcas", description = "Operaciones para gestionar marcas de vehículos")
public class MarcaController {

    private final MarcaService marcaService;

    @Operation(summary = "Obtener todas las marcas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de marcas obtenida exitosamente",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    [
                      {
                        "id": "uuid-123",
                        "nombre": "Toyota",
                        "paisOrigen": "Japón"
                      }
                    ]
                """)))
    })
    @GetMapping
    public ResponseEntity<List<MarcaResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(marcaService.obtenerTodas());
    }

    @Operation(summary = "Obtener marca por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Marca encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = MarcaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Marca no encontrada",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "error": "Marca no encontrada" }
                """)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<MarcaResponseDTO> obtenerPorId(@PathVariable String id) {
        return ResponseEntity.ok(marcaService.obtenerPorId(id));
    }

    @Operation(summary = "Crear una nueva marca")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Marca creada exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = MarcaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "nombre": "El nombre es obligatorio" }
                """)))
    })
    @PostMapping
    public ResponseEntity<MarcaResponseDTO> guardar(
            @Valid @RequestBody MarcaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(marcaService.guardar(dto));
    }

    @Operation(summary = "Actualizar una marca existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Marca actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Marca no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<MarcaResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody MarcaRequestDTO dto) {
        return ResponseEntity.ok(marcaService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar una marca")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Marca eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Marca no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        marcaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}