package automotora.financiamiento_service.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import automotora.financiamiento_service.dto.request.FinanciamientoRequestDTO;
import automotora.financiamiento_service.dto.response.FinanciamientoResponseDTO;
import automotora.financiamiento_service.model.Financiamiento;
import automotora.financiamiento_service.repository.FinanciamientoRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FinanciamientoService {

    private final FinanciamientoRepository repository;

    public List<FinanciamientoResponseDTO> obtenerTodos() {
        return repository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public FinanciamientoResponseDTO obtenerPorId(String id) {

        Financiamiento financiamiento = repository.findById(id)
                .orElseThrow();

        return convertirDTO(financiamiento);
    }

    public FinanciamientoResponseDTO guardar(
            FinanciamientoRequestDTO dto) {

        Financiamiento financiamiento = Financiamiento.builder()
                .numeroCuotas(dto.getNumeroCuotas())
                .tasaInteres(dto.getTasaInteres())
                .montoSolicitado(dto.getMontoSolicitado())
                .pie(dto.getPie())
                .valorCuota(dto.getValorCuota())
                .estadoFinanciamiento(dto.getEstadoFinanciamiento())
                .ventaId(dto.getVentaId())
                .build();

        return convertirDTO(
                repository.save(financiamiento));
    }

    public FinanciamientoResponseDTO actualizar(
            String id,
            FinanciamientoRequestDTO dto) {

        Financiamiento financiamiento = repository.findById(id)
                .orElseThrow();

        financiamiento.setNumeroCuotas(dto.getNumeroCuotas());
        financiamiento.setTasaInteres(dto.getTasaInteres());
        financiamiento.setMontoSolicitado(dto.getMontoSolicitado());
        financiamiento.setPie(dto.getPie());
        financiamiento.setValorCuota(dto.getValorCuota());
        financiamiento.setEstadoFinanciamiento(dto.getEstadoFinanciamiento());
        financiamiento.setVentaId(dto.getVentaId());

        return convertirDTO(
                repository.save(financiamiento));
    }

    public void eliminar(String id) {

        Financiamiento financiamiento = repository.findById(id)
                .orElseThrow();

        repository.delete(financiamiento);
    }

    private FinanciamientoResponseDTO convertirDTO(
            Financiamiento financiamiento) {

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