package com.automotora.vehiculo_service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "vehiculo")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Vehiculo {

    @Id
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