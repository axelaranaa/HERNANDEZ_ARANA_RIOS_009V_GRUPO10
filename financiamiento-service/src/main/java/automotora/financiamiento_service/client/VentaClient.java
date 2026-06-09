package automotora.financiamiento_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
        name = "venta-service",
        url = "http://localhost:8095"
)
public interface VentaClient {

    @GetMapping("/api/ventas/{id}")
    Object obtenerVenta(@PathVariable String id);
}