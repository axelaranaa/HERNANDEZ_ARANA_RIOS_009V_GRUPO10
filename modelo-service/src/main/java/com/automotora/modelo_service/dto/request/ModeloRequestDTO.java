package com.automotora.modelo_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModeloRequestDTO {

    @NotBlank(message = "El nombre del modelo es obligatorio")
    private String nombreModelo;

    @NotBlank(message = "El tipo de vehículo es obligatorio")
    private String tipoVehiculo;

    @NotBlank(message = "La marca es obligatoria")
    private String marcaId;
}