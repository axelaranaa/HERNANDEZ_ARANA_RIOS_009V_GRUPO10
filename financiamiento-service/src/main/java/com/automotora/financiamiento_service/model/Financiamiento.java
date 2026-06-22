package com.automotora.financiamiento_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Optional;

import com.automotora.financiamiento_service.dto.request.FinanciamientoRequestDTO;
import com.automotora.financiamiento_service.dto.response.FinanciamientoResponseDTO;

@Entity
@Table(name = "financiamientos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Financiamiento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "monto_solicitado", nullable = false)
    private Double montoSolicitado;

    @Column(name = "cantidad_cuotas", nullable = false)
    private Integer cantidadCuotas;

    @Column(name = "tasa_interes", nullable = false)
    private Double tasaInteres;

    @Column(name = "estado_solicitud", nullable = false) // ej: PENDIENTE, APROBADO, RECHAZADO
    private String estadoSolicitud;

    @Column(name = "fecha_solicitud", nullable = false)
    private LocalDate fechaSolicitud;

    @Column(name = "venta_id", nullable = false)
    private String ventaId; // Relación lógica con el microservicio de ventas

    public static void obtenerPorId(String string) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'obtenerPorId'");
    }

    public static FinanciamientoResponseDTO crear(FinanciamientoRequestDTO request) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'crear'");
    }

    public static Optional<Financiamiento> findById(String id2) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
}