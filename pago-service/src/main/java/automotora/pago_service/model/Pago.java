package automotora.pago_service.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "pagos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Pago {

    @Id
    private String id;

    @Column(nullable = false)
    private String metodoPago;

    @Column(nullable = false)
    private BigDecimal montoAbonado;

    @Column(nullable = false)
    private LocalDate fechaPago;

    @Column(nullable = false)
    private String estadoPago;

    @Column(nullable = false)
    private String ventaId;

    @PrePersist
    public void generarId() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
    }
}