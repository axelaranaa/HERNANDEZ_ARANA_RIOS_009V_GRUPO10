package automotora.financiamiento_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class FinanciamientoResponseDTO {

    private String id;
    private Integer numeroCuotas;
    private BigDecimal tasaInteres;
    private BigDecimal montoSolicitado;
    private BigDecimal pie;
    private BigDecimal valorCuota;
    private String estadoFinanciamiento;
    private String ventaId;
}