package automotora.venta_service.dto.response;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaResponseDTO {

    private String id;
    private LocalDate fechaVenta;
    private BigDecimal montoTotal;
    private String estadoVenta;
    private String clienteId;
}