
package com.automotora.financiamiento_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FinanciamientoRequestDTO {

    @NotNull(message = "El monto solicitado no puede ser nulo")
    @Positive(message = "El monto solicitado debe ser mayor a cero")
    private Double montoSolicitado;

    @NotNull(message = "La cantidad de cuotas no puede ser nula")
    @Min(value = 1, message = "La cantidad mínima de cuotas es 1")
    @Max(value = 72, message = "La cantidad máxima de cuotas permitida es 72")
    private Integer cantidadCuotas;

    @NotNull(message = "La tasa de interés no puede ser nula")
    @PositiveOrZero(message = "La tasa de interés no puede ser negativa")
    private Double tasaInteres;

    @NotBlank(message = "El estado de la solicitud no puede estar en blanco")
    private String estadoSolicitud;

    @NotNull(message = "La fecha de solicitud no puede ser nula")
    private LocalDate fechaSolicitud;

    @NotBlank(message = "El ID de la venta es obligatorio")
    private String ventaId;
}