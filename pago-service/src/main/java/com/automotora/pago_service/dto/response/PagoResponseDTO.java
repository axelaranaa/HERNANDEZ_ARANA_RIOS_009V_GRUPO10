package com.automotora.pago_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PagoResponseDTO {

    private String id;
    private String metodoPago;
    private BigDecimal montoAbonado;
    private LocalDate fechaPago;
    private String estadoPago;
    private String ventaId;
    public void setMontoAbonado(Double montoAbonado2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setMontoAbonado'");
    }
}