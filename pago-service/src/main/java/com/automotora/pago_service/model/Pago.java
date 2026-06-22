package com.automotora.pago_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "pagos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID) 
    private String id;

    @Column(name = "metodo_pago", nullable = false)
    private String metodoPago;

    @Column(name = "monto_abonado", nullable = false)
    private Double montoAbonado;

    @Column(name = "fecha_pago", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "estado_pago", nullable = false)
    private String estadoPago;

    @Column(name = "venta_id", nullable = false)
    private String ventaId; 
}