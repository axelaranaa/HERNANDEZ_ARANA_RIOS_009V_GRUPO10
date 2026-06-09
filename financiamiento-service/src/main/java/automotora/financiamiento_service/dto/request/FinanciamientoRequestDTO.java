package automotora.financiamiento_service.dto.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class FinanciamientoRequestDTO {

    private Integer numeroCuotas;
    private BigDecimal tasaInteres;
    private BigDecimal montoSolicitado;
    private BigDecimal pie;
    private BigDecimal valorCuota;
    private String estadoFinanciamiento;
    private String ventaId;
}