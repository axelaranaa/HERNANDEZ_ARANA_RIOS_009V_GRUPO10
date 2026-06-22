package com.automotora.reserva_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ReservaRequestDTO {

    @NotNull(message = "La fecha de reserva es obligatoria")
    private LocalDate fechaReserva;

    @NotBlank(message = "El estado de reserva es obligatorio")
    private String estadoReserva;

    @NotBlank(message = "El clienteId es obligatorio")
    private String clienteId;

    @NotBlank(message = "El vehiculoId es obligatorio")
    private String vehiculoId;
}
