package automotora.financiamiento_service.model;

import jakarta.persistence.*;
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

    private Integer numeroCuotas;

    private BigDecimal tasaInteres;

    private BigDecimal montoSolicitado;

    private BigDecimal pie;

    private BigDecimal valorCuota;

    private String estadoFinanciamiento;

    private String ventaId;

    @PrePersist
    public void generarId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}