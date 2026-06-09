package automotora.pago_service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class PagoRequestDTO {

    @NotBlank
    private String metodoPago;

    @NotNull
    private BigDecimal montoAbonado;

    @NotNull
    private LocalDate fechaPago;

    @NotBlank
    private String estadoPago;

    @NotBlank
    private String ventaId;
}