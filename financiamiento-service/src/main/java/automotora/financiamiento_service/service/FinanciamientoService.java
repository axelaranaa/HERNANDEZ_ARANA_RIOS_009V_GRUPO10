package automotora.financiamiento_service.service;

import automotora.financiamiento_service.client.VentaClient; // <--- Importación clave
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import automotora.financiamiento_service.dto.request.FinanciamientoRequestDTO;
import automotora.financiamiento_service.dto.response.FinanciamientoResponseDTO;
import automotora.financiamiento_service.model.Financiamiento;
import automotora.financiamiento_service.repository.FinanciamientoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FinanciamientoService {

    private final FinanciamientoRepository repository;
    private final VentaClient ventaClient; // <--- Inyectamos la comunicación por OpenFeign

    @Transactional(readOnly = true)
    public List<FinanciamientoResponseDTO> obtenerTodos() {
        log.info("Obteniendo todos los financiamientos");
        return repository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    @Transactional(readOnly = true)
    public FinanciamientoResponseDTO obtenerPorId(String id) {
        log.info("Buscando financiamiento con id {}", id);
        Financiamiento financiamiento = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Financiamiento no encontrado con ID: " + id));

        return convertirDTO(financiamiento);
    }

    @Transactional
    public FinanciamientoResponseDTO guardar(FinanciamientoRequestDTO dto) {
        log.info("Validando existencia de la venta ID: {} vía OpenFeign", dto.getVentaId());
        
        // Comunicación Síncrona: Verificamos si la venta existe en el microservicio correspondiente
        try {
            ventaClient.obtenerVenta(dto.getVentaId());
        } catch (Exception e) {
            log.error("Error al validar venta en financiamiento-service: Venta no encontrada");
            throw new IllegalArgumentException("No se puede registrar el financiamiento. La venta con ID '" + dto.getVentaId() + "' no existe.");
        }

        log.info("Guardando nueva solicitud de financiamiento");
        // Regla de negocio: Por defecto inicia en PENDIENTE si no se especifica
        String estado = (dto.getEstadoFinanciamiento() == null || dto.getEstadoFinanciamiento().isBlank()) 
                ? "PENDIENTE" 
                : dto.getEstadoFinanciamiento().toUpperCase();

        Financiamiento financiamiento = Financiamiento.builder()
                .numeroCuotas(dto.getNumeroCuotas())
                .tasaInteres(dto.getTasaInteres())
                .montoSolicitado(dto.getMontoSolicitado())
                .pie(dto.getPie())
                .valorCuota(dto.getValorCuota())
                .estadoFinanciamiento(estado)
                .ventaId(dto.getVentaId())
                .build();

        return convertirDTO(repository.save(financiamiento));
    }

    @Transactional
    public FinanciamientoResponseDTO actualizar(String id, FinanciamientoRequestDTO dto) {
        log.info("Actualizando financiamiento {}", id);
        Financiamiento financiamiento = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Financiamiento no encontrado con ID: " + id));

        log.info("Validando nueva venta ID: {} vía OpenFeign al actualizar", dto.getVentaId());
        // Validamos la venta también al actualizar
        try {
            ventaClient.obtenerVenta(dto.getVentaId());
        } catch (Exception e) {
            log.error("Error al validar venta en la actualización de financiamiento");
            throw new IllegalArgumentException("No se puede actualizar el financiamiento. La venta con ID '" + dto.getVentaId() + "' no existe.");
        }

        financiamiento.setNumeroCuotas(dto.getNumeroCuotas());
        financiamiento.setTasaInteres(dto.getTasaInteres());
        financiamiento.setMontoSolicitado(dto.getMontoSolicitado());
        financiamiento.setPie(dto.getPie());
        financiamiento.setValorCuota(dto.getValorCuota());
        financiamiento.setEstadoFinanciamiento(dto.getEstadoFinanciamiento().toUpperCase());
        financiamiento.setVentaId(dto.getVentaId());

        return convertirDTO(repository.save(financiamiento));
    }

    @Transactional
    public void eliminar(String id) {
        log.info("Eliminando financiamiento {}", id);
        Financiamiento financiamiento = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Financiamiento no encontrado con ID: " + id));

        repository.delete(financiamiento);
    }

    private FinanciamientoResponseDTO convertirDTO(Financiamiento financiamiento) {
        return FinanciamientoResponseDTO.builder()
                .id(financiamiento.getId())
                .numeroCuotas(financiamiento.getNumeroCuotas())
                .tasaInteres(financiamiento.getTasaInteres())
                .montoSolicitado(financiamiento.getMontoSolicitado())
                .pie(financiamiento.getPie())
                .valorCuota(financiamiento.getValorCuota())
                .estadoFinanciamiento(financiamiento.getEstadoFinanciamiento())
                .ventaId(financiamiento.getVentaId())
                .build();
    }
}