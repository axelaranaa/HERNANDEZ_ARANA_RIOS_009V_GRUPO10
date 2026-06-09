package com.automotora.servicio_tecnico_service.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class ServicioTecnicoRequestDTO {

    @NotNull
    private LocalDate fechaIngreso;

    private LocalDate fechaSalida;

    @NotBlank
    @Size(max = 500)
    private String diagnostico;

    @NotNull
    @DecimalMin("0.0")
    private BigDecimal costo;

    @NotBlank
    private String estadoServicio;

    @NotBlank
    private String vehiculoId;
}