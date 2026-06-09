package com.automotora.cliente_service.controller;

import com.automotora.cliente_service.dto.request.ClienteRequestDTO;
import com.automotora.cliente_service.dto.response.ClienteResponseDTO;
import com.automotora.cliente_service.service.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clientes")
@RequiredArgsConstructor
public class ClienteController {

    private final ClienteService clienteService;

    @GetMapping
    public ResponseEntity<List<ClienteResponseDTO>> obtenerTodos() {
        return ResponseEntity.ok(clienteService.obtenerTodos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> obtenerPorId(
            @PathVariable String id) {

        return ResponseEntity.ok(
                clienteService.obtenerPorId(id));
    }

    @PostMapping
    public ResponseEntity<ClienteResponseDTO> guardar(
            @Valid @RequestBody ClienteRequestDTO dto) {

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(clienteService.guardar(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizar(
            @PathVariable String id,
            @Valid @RequestBody ClienteRequestDTO dto) {

        return ResponseEntity.ok(
                clienteService.actualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable String id) {

        clienteService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}