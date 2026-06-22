package com.automotora.financiamiento_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanciamientoResponseDTO {
    private String id;
    private Double montoSolicitado;
    private Integer cantidadCuotas;
    private Double tasaInteres;
    private String estadoSolicitud;
    private LocalDate fechaSolicitud;
    private String ventaId;
}