package com.automotora.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Data;

@Entity
@Table(name = "FINANCIAMIENTO")
@Data
public class Financiamiento {
    @Id
    @Column(length = 36)
    private String id;

    @NotNull(message = "El monto financiado es obligatorio")
    @Min(value = 1, message = "El monto a financiar debe ser mayor a 0")
    private Double montoFinanciado;

    @NotNull(message = "La cantidad de cuotas es obligatoria")
    @Min(value = 1, message = "Debe ser al menos 1 cuota")
    private Integer cuotas;

    @NotNull(message = "La tasa de interés es obligatoria")
    private Double tasaInteres;

    @Column(name = "VENTA_id", length = 36)
    private String ventaId;
}