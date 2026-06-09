package automotora.venta_service.service;

import automotora.venta_service.dto.request.VentaRequestDTO;
import automotora.venta_service.dto.response.VentaResponseDTO;
import automotora.venta_service.exception.VentaNotFoundException;
import automotora.venta_service.model.Venta;
import automotora.venta_service.repository.VentaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class VentaService {

    private final VentaRepository ventaRepository;

    public List<VentaResponseDTO> obtenerTodas() {

        log.info("Obteniendo todas las ventas");

        return ventaRepository.findAll()
                .stream()
                .map(this::convertirADTO)
                .toList();
    }

    public VentaResponseDTO obtenerPorId(String id) {

        log.info("Buscando venta con id {}", id);

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new VentaNotFoundException(id));

        return convertirADTO(venta);
    }

    public VentaResponseDTO guardar(VentaRequestDTO dto) {

        log.info("Creando nueva venta");

        Venta venta = Venta.builder()
                .fechaVenta(dto.getFechaVenta())
                .montoTotal(dto.getMontoTotal())
                .estadoVenta(dto.getEstadoVenta())
                .clienteId(dto.getClienteId())
                .build();

        return convertirADTO(
                ventaRepository.save(venta)
        );
    }

    public VentaResponseDTO actualizar(String id, VentaRequestDTO dto) {

        log.info("Actualizando venta {}", id);

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new VentaNotFoundException(id));

        venta.setFechaVenta(dto.getFechaVenta());
        venta.setMontoTotal(dto.getMontoTotal());
        venta.setEstadoVenta(dto.getEstadoVenta());
        venta.setClienteId(dto.getClienteId());

        return convertirADTO(
                ventaRepository.save(venta)
        );
    }

    public void eliminar(String id) {

        log.info("Eliminando venta {}", id);

        Venta venta = ventaRepository.findById(id)
                .orElseThrow(() -> new VentaNotFoundException(id));

        ventaRepository.delete(venta);
    }

    private VentaResponseDTO convertirADTO(Venta venta) {

        return VentaResponseDTO.builder()
                .id(venta.getId())
                .fechaVenta(venta.getFechaVenta())
                .montoTotal(venta.getMontoTotal())
                .estadoVenta(venta.getEstadoVenta())
                .clienteId(venta.getClienteId())
                .build();
    }
}