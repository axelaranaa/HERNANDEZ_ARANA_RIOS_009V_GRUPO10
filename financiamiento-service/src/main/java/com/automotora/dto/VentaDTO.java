package com.automotora.dto;



import lombok.Data;

@Data 
public class VentaDTO {
    private String id;
    private String clienteId;
    private String vehiculoId;
    private Double precioTotal;
    private String estado; // Por ejemplo: PENDIENTE, PAGADA, etc.
}