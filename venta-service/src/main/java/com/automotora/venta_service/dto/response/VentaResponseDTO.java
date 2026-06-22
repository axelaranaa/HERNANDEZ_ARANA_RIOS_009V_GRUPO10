package com.automotora.venta_service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentaResponseDTO {
    
    private String id;
    private Double montoTotal;
    private String estadoVenta;
    private String clienteId;
    private String vehiculoId;
}