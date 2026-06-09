package automotora.venta_service.exception;

public class VentaNotFoundException extends RuntimeException {

    public VentaNotFoundException(String id) {
        super("Venta no encontrada con id: " + id);
    }
}