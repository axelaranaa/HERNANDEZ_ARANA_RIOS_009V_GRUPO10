package com.automotora.vehiculo_service.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "vehiculo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String patente;

    private Integer anio;

    private BigDecimal kilometraje;

    private BigDecimal precio;

    private String color;

    private String transmision;

    private String combustible;

    private String estado;

    @Column(name = "modelo_id")
    private String modeloId;
}