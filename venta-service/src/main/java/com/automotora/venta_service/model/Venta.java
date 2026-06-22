package com.automotora.venta_service.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ventas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) // Genera automáticamente IDs únicos de texto (ej: UUID-VENTA-001)
    private String id;

    @Column(name = "monto_total", nullable = false)
    private Double montoTotal;

    @Column(name = "estado_venta", nullable = false)
    private String estadoVenta;

    @Column(name = "cliente_id", nullable = false)
    private String clienteId;

    @Column(name = "vehiculo_id", nullable = false)
    private String vehiculoId;
}