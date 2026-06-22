package com.automotora.vehiculo_service.dto.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModeloResponseDTO {
    private String id;
    private String nombreModelo;
    private String tipoVehiculo;
    private String marcaId;
}