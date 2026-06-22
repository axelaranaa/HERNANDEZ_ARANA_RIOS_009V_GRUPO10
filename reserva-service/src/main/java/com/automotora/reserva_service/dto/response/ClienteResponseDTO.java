package com.automotora.reserva_service.dto.response;

import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClienteResponseDTO {
    private String id;
    private String rut;
    private String dv;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private String direccion;
    private LocalDate fechaRegistro;
    private String estado;
    private String usuarioId;
}
