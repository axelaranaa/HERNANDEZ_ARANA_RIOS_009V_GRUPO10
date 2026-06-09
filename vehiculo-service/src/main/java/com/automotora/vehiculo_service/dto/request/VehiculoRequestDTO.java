package com.automotora.vehiculo_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class VehiculoRequestDTO {

    @NotBlank
    private String patente;

    @NotNull
    private Integer anio;

    @NotNull
    private BigDecimal kilometraje;

    @NotNull
    private BigDecimal precio;

    @NotBlank
    private String color;

    @NotBlank
    private String transmision;

    @NotBlank
    private String combustible;

    @NotBlank
    private String estado;

    @NotBlank
    private String modeloId;
}