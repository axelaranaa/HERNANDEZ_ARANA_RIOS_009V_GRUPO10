package com.automotora.venta_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequestDTO {

    @NotNull(message = "El monto total no puede ser nulo")
    @Positive(message = "El monto total debe ser un número positivo")
    private Double montoTotal;

    @NotBlank(message = "El estado de la venta no puede estar en blanco")
    private String estadoVenta;

    @NotBlank(message = "El ID del cliente no puede estar en blanco")
    private String clienteId;

    @NotBlank(message = "El ID del vehículo no puede estar en blanco")
    private String vehiculoId;
}