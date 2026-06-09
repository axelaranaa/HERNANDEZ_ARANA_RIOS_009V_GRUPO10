package com.automotora.marca_service.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MarcaResponseDTO {

    private String id;
    private String nombreMarca;
}