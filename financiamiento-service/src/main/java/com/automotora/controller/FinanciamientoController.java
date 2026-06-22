package com.automotora.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.automotora.dto.request.FinanciamientoRequestDTO;   
import com.automotora.dto.response.FinanciamientoResponseDTO; 
import com.automotora.service.FinanciamientoService;

@RestController
@RequestMapping("/api/v1/financiamientos")
public class FinanciamientoController {

    @Autowired
    private FinanciamientoService service;

    // Recibe el FinanciamientoRequestDTO y responde un FinanciamientoResponseDTO con estado 201 (Created)
    @PostMapping
    public ResponseEntity<FinanciamientoResponseDTO> crearFinanciamiento(@Valid @RequestBody FinanciamientoRequestDTO request) {
        FinanciamientoResponseDTO respuesta = service.procesarFinanciamiento(request);
        return new ResponseEntity<>(respuesta, HttpStatus.CREATED);
    }
}