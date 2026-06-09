package com.automotora.modelo_service.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "modelo")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Modelo {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "nombre_modelo", nullable = false, length = 50)
    private String nombreModelo;

    @Column(name = "tipo_vehiculo", nullable = false, length = 50)
    private String tipoVehiculo;

    @Column(name = "marca_id", nullable = false, length = 36)
    private String marcaId;
}