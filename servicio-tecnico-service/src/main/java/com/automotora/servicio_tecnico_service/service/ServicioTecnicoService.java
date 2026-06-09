package com.automotora.servicio_tecnico_service.service;

import com.automotora.servicio_tecnico_service.client.VehiculoClient;
import com.automotora.servicio_tecnico_service.dto.request.ServicioTecnicoRequestDTO;
import com.automotora.servicio_tecnico_service.dto.response.ServicioTecnicoResponseDTO;
import com.automotora.servicio_tecnico_service.exception.ServicioTecnicoNotFoundException;
import com.automotora.servicio_tecnico_service.model.ServicioTecnico;
import com.automotora.servicio_tecnico_service.repository.ServicioTecnicoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class ServicioTecnicoService {

    private final ServicioTecnicoRepository repository;
    private final VehiculoClient vehiculoClient;

    public List<ServicioTecnicoResponseDTO> obtenerTodos() {

        log.info("Obteniendo servicios técnicos");

        return repository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public ServicioTecnicoResponseDTO obtenerPorId(String id) {

        return convertirDTO(buscar(id));
    }

    public ServicioTecnicoResponseDTO guardar(
            ServicioTecnicoRequestDTO dto) {

        vehiculoClient.obtenerVehiculo(dto.getVehiculoId());

        ServicioTecnico servicio = ServicioTecnico.builder()
                .id(UUID.randomUUID().toString())
                .fechaIngreso(dto.getFechaIngreso())
                .fechaSalida(dto.getFechaSalida())
                .diagnostico(dto.getDiagnostico())
                .costo(dto.getCosto())
                .estadoServicio(dto.getEstadoServicio())
                .vehiculoId(dto.getVehiculoId())
                .build();

        log.info("Guardando servicio técnico");

        return convertirDTO(
                repository.save(servicio)
        );
    }

    public ServicioTecnicoResponseDTO actualizar(
            String id,
            ServicioTecnicoRequestDTO dto) {

        ServicioTecnico servicio = buscar(id);

        vehiculoClient.obtenerVehiculo(dto.getVehiculoId());

        servicio.setFechaIngreso(dto.getFechaIngreso());
        servicio.setFechaSalida(dto.getFechaSalida());
        servicio.setDiagnostico(dto.getDiagnostico());
        servicio.setCosto(dto.getCosto());
        servicio.setEstadoServicio(dto.getEstadoServicio());
        servicio.setVehiculoId(dto.getVehiculoId());

        log.info("Actualizando servicio {}", id);

        return convertirDTO(
                repository.save(servicio)
        );
    }

    public void eliminar(String id) {

        ServicioTecnico servicio = buscar(id);

        log.info("Eliminando servicio {}", id);

        repository.delete(servicio);
    }

    private ServicioTecnico buscar(String id) {

        return repository.findById(id)
                .orElseThrow(() ->
                        new ServicioTecnicoNotFoundException(
                                "Servicio técnico no encontrado con id: " + id));
    }

    private ServicioTecnicoResponseDTO convertirDTO(
            ServicioTecnico servicio) {

        return ServicioTecnicoResponseDTO.builder()
                .id(servicio.getId())
                .fechaIngreso(servicio.getFechaIngreso())
                .fechaSalida(servicio.getFechaSalida())
                .diagnostico(servicio.getDiagnostico())
                .costo(servicio.getCosto())
                .estadoServicio(servicio.getEstadoServicio())
                .vehiculoId(servicio.getVehiculoId())
                .build();
    }
}