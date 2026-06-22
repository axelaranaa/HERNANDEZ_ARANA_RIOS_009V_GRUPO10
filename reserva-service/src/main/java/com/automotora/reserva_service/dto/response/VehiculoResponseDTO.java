package com.automotora.reserva_service.dto.response;

import lombok.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
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
