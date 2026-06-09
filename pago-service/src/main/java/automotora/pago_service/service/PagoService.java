package automotora.pago_service.service;

import automotora.pago_service.dto.request.PagoRequestDTO;
import automotora.pago_service.model.Pago;
import automotora.pago_service.repository.PagoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PagoService {

    private final PagoRepository pagoRepository;

    public List<Pago> obtenerTodos() {
        log.info("Obteniendo todos los pagos");
        return pagoRepository.findAll();
    }

    public Pago obtenerPorId(String id) {
        log.info("Buscando pago con id: {}", id);
        return pagoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pago no encontrado"));
    }

    public Pago crear(PagoRequestDTO dto) {

        Pago pago = new Pago();

        pago.setId(UUID.randomUUID().toString());
        pago.setMetodoPago(dto.getMetodoPago());
        pago.setMontoAbonado(dto.getMontoAbonado());
        pago.setFechaPago(dto.getFechaPago());
        pago.setEstadoPago(dto.getEstadoPago());
        pago.setVentaId(dto.getVentaId());

        log.info("Creando pago {}", pago.getId());

        return pagoRepository.save(pago);
    }

    public Pago actualizar(String id, PagoRequestDTO dto) {

        Pago pago = obtenerPorId(id);

        pago.setMetodoPago(dto.getMetodoPago());
        pago.setMontoAbonado(dto.getMontoAbonado());
        pago.setFechaPago(dto.getFechaPago());
        pago.setEstadoPago(dto.getEstadoPago());
        pago.setVentaId(dto.getVentaId());

        log.info("Actualizando pago {}", id);

        return pagoRepository.save(pago);
    }

    public void eliminar(String id) {

        Pago pago = obtenerPorId(id);

        log.info("Eliminando pago {}", id);

        pagoRepository.delete(pago);
    }
}