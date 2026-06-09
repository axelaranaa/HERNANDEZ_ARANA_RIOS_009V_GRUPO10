package com.automotora.reserva_service.dto.response;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReservaResponseDTO {

    private String id;
    private LocalDate fechaReserva;
    private String estadoReserva;
    private String clienteId;
    private String vehiculoId;
}