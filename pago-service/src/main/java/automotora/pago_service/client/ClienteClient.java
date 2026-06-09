package automotora.pago_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "cliente-service",
        url = "http://localhost:8094"
)
public interface ClienteClient {

    @GetMapping("/api/clientes/{id}")
    Object obtenerCliente(
            @PathVariable String id);
}