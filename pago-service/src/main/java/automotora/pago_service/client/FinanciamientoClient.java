package automotora.pago_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "financiamiento-service",
        url = "http://localhost:8096"
)
public interface FinanciamientoClient {

    @GetMapping("/api/financiamientos/{id}")
    Object obtenerFinanciamiento(
            @PathVariable String id);
}