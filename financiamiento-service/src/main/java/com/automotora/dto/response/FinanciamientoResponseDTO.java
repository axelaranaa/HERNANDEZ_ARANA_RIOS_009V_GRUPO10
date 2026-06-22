package com.automotora.dto.response;

import lombok.Data;

@Data
public class FinanciamientoResponseDTO {
    private String id;
    private String ventaId;
    private Double montoFinanciado;
    private Integer cuotas;
}