package com.automotora.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class FinanciamientoRequestDTO {

    @NotBlank(message = "El ID de la venta es obligatorio.")
    private String ventaId;

    @NotNull(message = "El monto a financiar es obligatorio.")
    @Positive(message = "El monto a financiar debe ser un número positivo.")
    private Double montoFinanciado;

    @NotNull(message = "El número de cuotas es obligatorio.")
    @Min(value = 1, message = "El número de cuotas debe ser al menos 1.")
    private Integer cuotas;
}