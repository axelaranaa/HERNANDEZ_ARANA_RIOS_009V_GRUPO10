package com.automotora.cliente_service.controller;

import com.automotora.cliente_service.dto.request.ClienteRequestDTO;
import com.automotora.cliente_service.dto.response.ClienteResponseDTO;
import com.automotora.cliente_service.service.ClienteService;
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
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
@Tag(name = "Clientes", description = "Operaciones para gestionar clientes de la automotora")
public class ClienteController {

    private final ClienteService clienteService;

    @Operation(summary = "Obtener todos los clientes")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida exitosamente",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    [
                      {
                        "id": "uuid-123",
                        "rut": "12345678",
                        "dv": "9",
                        "nombre": "Juan",
                        "apellido": "Pérez",
                        "email": "jperez@mail.com",
                        "telefono": "+56912345678",
                        "direccion": "Av. Principal 123",
                        "fechaRegistro": "2024-01-10",
                        "estado": "ACTIVO",
                        "usuarioId": "uuid-usuario"
                      }
                    ]
                """)))
    })
    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }

    @Operation(summary = "Obtener cliente por ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente encontrado",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ClienteResponseDTO.class))),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "error": "Cliente no encontrado con id: uuid-123" }
                """)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerPorId(
            @PathVariable String id) {
        return ResponseEntity.ok(clienteService.obtenerPorId(id));
    }

    @Operation(summary = "Crear un nuevo cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente",
            content = @Content(mediaType = "application/json",
                schema = @Schema(implementation = ClienteResponseDTO.class))),
        @ApiResponse(responseCode = "400", description = "Datos inválidos",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "rut": "El rut es obligatorio" }
                """))),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado",
            content = @Content(mediaType = "application/json",
                examples = @ExampleObject(value = """
                    { "error": "El usuario con id: uuid-usuario no existe" }
                """)))
    })
    @PostMapping
    public ResponseEntity<ClienteResponseDTO> guardar(
            @Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.guardar(dto));
    }

    @Operation(summary = "Actualizar un cliente existente")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody ClienteRequestDTO dto) {
        return ResponseEntity.ok(clienteService.actualizar(id, dto));
    }

    @Operation(summary = "Eliminar un cliente")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        clienteService.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
