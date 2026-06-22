package automotora.financiamiento_service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "financiamiento")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Financiamiento {

    @Id
    private String id;

    @NotNull(message = "El número de cuotas es obligatorio")
    @Min(value = 1, message = "El número de cuotas debe ser al menos 1")
    private Integer numeroCuotas;

    @NotNull(message = "La tasa de interés es obligatoria")
    @PositiveOrZero(message = "La tasa de interés no puede ser negativa")
    private BigDecimal tasaInteres;

    @NotNull(message = "El monto solicitado es obligatorio")
    @Positive(message = "El monto solicitado debe ser mayor a cero")
    private BigDecimal montoSolicitado;

    @NotNull(message = "El valor del pie es obligatorio")
    @PositiveOrZero(message = "El pie no puede ser negativo")
    private BigDecimal pie;

    @NotNull(message = "El valor de la cuota es obligatorio")
    @Positive(message = "El valor de la cuota debe ser mayor a cero")
    private BigDecimal valorCuota;

    @NotBlank(message = "El estado del financiamiento es obligatorio")
    private String estadoFinanciamiento; // PENDIENTE, APROBADO, etc.

    @NotBlank(message = "El ID de la venta asociada es obligatorio")
    private String ventaId; // Enlace clave para OpenFeign

    @PrePersist
    public void generarId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}