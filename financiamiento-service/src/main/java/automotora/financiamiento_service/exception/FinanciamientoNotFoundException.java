package automotora.financiamiento_service.exception;

public class FinanciamientoNotFoundException extends RuntimeException {

    public FinanciamientoNotFoundException(String mensaje) {
        super(mensaje);
    }
}