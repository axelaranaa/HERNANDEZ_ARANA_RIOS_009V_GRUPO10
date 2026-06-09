package automotora.venta_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "pago-service",
        url = "http://localhost:8097"
)
public interface PagoClient {

    @GetMapping("/api/pagos/{id}")
    Object obtenerPago(
            @PathVariable String id);
}