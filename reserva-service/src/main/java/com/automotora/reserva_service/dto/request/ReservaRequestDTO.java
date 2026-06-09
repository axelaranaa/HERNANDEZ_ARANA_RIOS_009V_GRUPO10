package com.automotora.reserva_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReservaRequestDTO {

    @NotNull
    private LocalDate fechaReserva;

    @NotBlank
    private String estadoReserva;

    @NotBlank
    private String clienteId;

    @NotBlank
    private String vehiculoId;
}