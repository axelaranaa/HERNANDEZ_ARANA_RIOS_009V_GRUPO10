package com.automotora.marca_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MarcaRequestDTO {

    @NotBlank(message = "El nombre de la marca es obligatorio")
    private String nombreMarca;

    
}