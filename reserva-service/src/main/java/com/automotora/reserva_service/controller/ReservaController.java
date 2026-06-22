package com.automotora.reserva_service.controller;

import com.automotora.reserva_service.dto.request.ReservaRequestDTO;
import com.automotora.reserva_service.dto.response.ReservaResponseDTO;
import com.automotora.reserva_service.service.ReservaService;
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
@RequestMapping("/api/reservas")
@RequiredArgsConstructor
@Tag(name = "Reservas", description = "Operaciones para gestionar reservas de vehículos")
public class ReservaController {

    private final ReservaService reservaService;

    @Operation(summary = "Obtener todas las reservas")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de reservas obtenida exitosamente",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    [
                      {
                        "id": "uuid-123",
                        "fechaReserva": "2024-03-15",
                        "estadoReserva": "PENDIENTE",
                        "clienteId": "uuid-cliente",
                        "vehiculoId": "uuid-vehiculo"
                      }
                    ]
                """)))
    })
    @GetMapping
    public ResponseEntity<List<ReservaResponseDTO>> obtenerTodas() {
        return ResponseEntity.ok(reservaService.obtenerTodas());
    }

    @Operation(summary = "Obtener reserva por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reserva encontrada",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ReservaResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "error": "Reserva no encontrada con id: uuid-123" }
                """)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> obtenerPorId(
            @PathVariable String id) {
        return ResponseEntity.ok(reservaService.obtenerPorId(id));
    }

    @Operation(summary = "Crear una nueva reserva")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Reserva creada exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ReservaResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "clienteId": "El clienteId es obligatorio" }
                """))),
        @ApiResponse(responseCode = "404", description = "Cliente o vehículo no encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "error": "El cliente con id: uuid-cliente no existe" }
                """)))
    })
    @PostMapping
    public ResponseEntity<ReservaResponseDTO> guardar(
            @Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reservaService.guardar(dto));
    }

    @Operation(summary = "Actualizar una reserva existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Reserva actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ReservaResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody ReservaRequestDTO dto) {
        return ResponseEntity.ok(reservaService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar una reserva")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Reserva eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reserva no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        reservaService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
