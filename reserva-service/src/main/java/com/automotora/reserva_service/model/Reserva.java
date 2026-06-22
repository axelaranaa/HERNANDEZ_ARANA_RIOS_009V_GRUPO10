package com.automotora.reserva_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "reservas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "fecha_reserva")
    private LocalDate fechaReserva;

    @Column(name = "estado_reserva")
    private String estadoReserva;

    @Column(name = "cliente_id")
    private String clienteId;

    @Column(name = "vehiculo_id")
    private String vehiculoId;
}
