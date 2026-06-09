package com.automotora.vehiculo_service.service;

import com.automotora.vehiculo_service.client.ModeloClient;
import com.automotora.vehiculo_service.dto.request.VehiculoRequestDTO;
import com.automotora.vehiculo_service.dto.response.VehiculoResponseDTO;
import com.automotora.vehiculo_service.exception.VehiculoNotFoundException;
import com.automotora.vehiculo_service.model.Vehiculo;
import com.automotora.vehiculo_service.repository.VehiculoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class VehiculoService {

    private final VehiculoRepository vehiculoRepository;
    private final ModeloClient modeloClient;

    public List<VehiculoResponseDTO> obtenerTodos() {

        log.info("Obteniendo todos los vehículos");

        return vehiculoRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public VehiculoResponseDTO obtenerPorId(String id) {

        Vehiculo vehiculo = buscarVehiculo(id);

        return convertirDTO(vehiculo);
    }

    public VehiculoResponseDTO guardar(VehiculoRequestDTO dto) {

        log.info("Validando existencia del modelo {}", dto.getModeloId());

        modeloClient.obtenerModelo(dto.getModeloId());

        Vehiculo vehiculo = Vehiculo.builder()
                .id(UUID.randomUUID().toString())
                .patente(dto.getPatente())
                .anio(dto.getAnio())
                .kilometraje(dto.getKilometraje())
                .precio(dto.getPrecio())
                .color(dto.getColor())
                .transmision(dto.getTransmision())
                .combustible(dto.getCombustible())
                .estado(dto.getEstado())
                .modeloId(dto.getModeloId())
                .build();

        log.info("Guardando vehículo {}", dto.getPatente());

        return convertirDTO(
                vehiculoRepository.save(vehiculo)
        );
    }

    public VehiculoResponseDTO actualizar(
            String id,
            VehiculoRequestDTO dto) {

        Vehiculo vehiculo = buscarVehiculo(id);

        modeloClient.obtenerModelo(dto.getModeloId());

        vehiculo.setPatente(dto.getPatente());
        vehiculo.setAnio(dto.getAnio());
        vehiculo.setKilometraje(dto.getKilometraje());
        vehiculo.setPrecio(dto.getPrecio());
        vehiculo.setColor(dto.getColor());
        vehiculo.setTransmision(dto.getTransmision());
        vehiculo.setCombustible(dto.getCombustible());
        vehiculo.setEstado(dto.getEstado());
        vehiculo.setModeloId(dto.getModeloId());

        log.info("Actualizando vehículo {}", id);

        return convertirDTO(
                vehiculoRepository.save(vehiculo)
        );
    }

    public void eliminar(String id) {

        Vehiculo vehiculo = buscarVehiculo(id);

        log.info("Eliminando vehículo {}", id);

        vehiculoRepository.delete(vehiculo);
    }

    private Vehiculo buscarVehiculo(String id) {

        return vehiculoRepository.findById(id)
                .orElseThrow(() ->
                        new VehiculoNotFoundException(
                                "Vehículo no encontrado con id: " + id));
    }

    private VehiculoResponseDTO convertirDTO(Vehiculo vehiculo) {

        return VehiculoResponseDTO.builder()
                .id(vehiculo.getId())
                .patente(vehiculo.getPatente())
                .anio(vehiculo.getAnio())
                .kilometraje(vehiculo.getKilometraje())
                .precio(vehiculo.getPrecio())
                .color(vehiculo.getColor())
                .transmision(vehiculo.getTransmision())
                .combustible(vehiculo.getCombustible())
                .estado(vehiculo.getEstado())
                .modeloId(vehiculo.getModeloId())
                .build();
    }
}