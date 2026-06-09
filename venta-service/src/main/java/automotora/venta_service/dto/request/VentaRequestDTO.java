package automotora.venta_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VentaRequestDTO {

    @NotNull
    private LocalDate fechaVenta;

    @NotNull
    private BigDecimal montoTotal;

    @NotBlank
    private String estadoVenta;

    @NotBlank
    private String clienteId;
}