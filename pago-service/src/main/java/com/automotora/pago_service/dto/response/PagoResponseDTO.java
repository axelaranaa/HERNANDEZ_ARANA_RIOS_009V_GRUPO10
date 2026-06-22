package com.automotora.pago_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoResponseDTO {

    private String id;
    private String metodoPago;
    private Double montoAbonado; // Unificado a Double
    private LocalDate fechaPago;
    private String estadoPago;
    private String ventaId;
}