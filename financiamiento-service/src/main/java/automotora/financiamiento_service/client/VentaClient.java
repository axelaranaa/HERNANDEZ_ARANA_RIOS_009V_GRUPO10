package automotora.financiamiento_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// Removemos la URL fija para que trabaje de forma dinámica balanceando con Eureka
@FeignClient(name = "venta-service")
public interface VentaClient {

    // Apuntamos a la versión definitiva del endpoint de ventas con /v1
    @GetMapping("/api/v1/ventas/{id}")
    Object obtenerVenta(@PathVariable("id") String id);
}