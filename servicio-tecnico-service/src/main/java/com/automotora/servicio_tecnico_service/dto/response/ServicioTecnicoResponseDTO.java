package com.automotora.servicio_tecnico_service.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicioTecnicoResponseDTO {

    private String id;
    private LocalDate fechaIngreso;
    private LocalDate fechaSalida;
    private String diagnostico;
    private BigDecimal costo;
    private String estadoServicio;
    private String vehiculoId;
}