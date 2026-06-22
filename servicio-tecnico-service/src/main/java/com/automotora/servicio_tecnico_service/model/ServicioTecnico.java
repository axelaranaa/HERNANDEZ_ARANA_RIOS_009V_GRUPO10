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
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "fecha_ingreso")
    private LocalDate fechaIngreso;

    @Column(name = "fecha_salida")
    private LocalDate fechaSalida;

    @Column(length = 500)
    private String diagnostico;

    private BigDecimal costo;

    @Column(name = "estado_servicio")
    private String estadoServicio;

    @Column(name = "vehiculo_id")
    private String vehiculoId;
}