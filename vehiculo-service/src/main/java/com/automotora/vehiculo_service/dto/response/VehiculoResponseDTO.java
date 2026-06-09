package com.automotora.vehiculo_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehiculoResponseDTO {

    private String id;
    private String patente;
    private Integer anio;
    private BigDecimal kilometraje;
    private BigDecimal precio;
    private String color;
    private String transmision;
    private String combustible;
    private String estado;
    private String modeloId;
}