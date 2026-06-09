package com.automotora.servicio_tecnico_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "servicio_tecnico")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ServicioTecnico {

    @Id
    private String id;

    private LocalDate fechaIngreso;

    private LocalDate fechaSalida;

    @Column(length = 500)
    private String diagnostico;

    private BigDecimal costo;

    private String estadoServicio;

    private String vehiculoId;
}