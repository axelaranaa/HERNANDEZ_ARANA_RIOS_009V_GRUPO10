package com.automotora.pago_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagoRequestDTO {

    @NotBlank(message = "El método de pago no puede estar en blanco")
    private String metodoPago;

    @NotNull(message = "El monto abonado no puede ser nulo")
    @Positive(message = "El monto abonado debe ser un número positivo")
    private Double montoAbonado; // Corregido a Double para unificar tipos

    @NotNull(message = "La fecha de pago no puede ser nula")
    private LocalDate fechaPago;

    @NotBlank(message = "El estado de pago no puede estar en blanco")
    private String estadoPago;

    @NotBlank(message = "El ID de la venta no puede estar en blanco")
    private String ventaId;
}