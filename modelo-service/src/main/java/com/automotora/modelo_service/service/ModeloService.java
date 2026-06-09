package com.automotora.modelo_service.service;

import com.automotora.modelo_service.client.MarcaClient;
import com.automotora.modelo_service.dto.request.ModeloRequestDTO;
import com.automotora.modelo_service.dto.response.ModeloResponseDTO;
import com.automotora.modelo_service.exception.ModeloNotFoundException;
import com.automotora.modelo_service.model.Modelo;
import com.automotora.modelo_service.repository.ModeloRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ModeloService {

    private final ModeloRepository modeloRepository;
    private final MarcaClient marcaClient;

    private static final List<String> TIPOS_VALIDOS = List.of(
            "Sedan",
            "SUV",
            "Hatchback",
            "Camioneta",
            "Coupe"
    );

    public List<ModeloResponseDTO> obtenerTodos() {

        log.info("Obteniendo todos los modelos");

        return modeloRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public ModeloResponseDTO obtenerPorId(String id) {

        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() ->
                        new ModeloNotFoundException(
                                "Modelo no encontrado"));

        return convertirDTO(modelo);
    }

    public ModeloResponseDTO guardar(ModeloRequestDTO dto) {

        marcaClient.obtenerMarcaPorId(dto.getMarcaId());

        if (!TIPOS_VALIDOS.contains(dto.getTipoVehiculo())) {
            throw new RuntimeException(
                    "Tipo de vehículo no válido");
        }

        if (modeloRepository.existsByNombreModeloAndMarcaId(
                dto.getNombreModelo(),
                dto.getMarcaId())) {

            throw new RuntimeException(
                    "Ya existe un modelo con ese nombre para esta marca");
        }

        Modelo modelo = Modelo.builder()
                .nombreModelo(dto.getNombreModelo())
                .tipoVehiculo(dto.getTipoVehiculo())
                .marcaId(dto.getMarcaId())
                .build();

        log.info("Creando modelo {}", dto.getNombreModelo());

        return convertirDTO(modeloRepository.save(modelo));
    }

    public ModeloResponseDTO actualizar(
            String id,
            ModeloRequestDTO dto) {

        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() ->
                        new ModeloNotFoundException(
                                "Modelo no encontrado"));

        marcaClient.obtenerMarcaPorId(dto.getMarcaId());

        if (!TIPOS_VALIDOS.contains(dto.getTipoVehiculo())) {
            throw new RuntimeException(
                    "Tipo de vehículo no válido");
        }

        modelo.setNombreModelo(dto.getNombreModelo());
        modelo.setTipoVehiculo(dto.getTipoVehiculo());
        modelo.setMarcaId(dto.getMarcaId());

        log.info("Actualizando modelo {}", id);

        return convertirDTO(modeloRepository.save(modelo));
    }

    public void eliminar(String id) {

        Modelo modelo = modeloRepository.findById(id)
                .orElseThrow(() ->
                        new ModeloNotFoundException(
                                "Modelo no encontrado"));

        log.info("Eliminando modelo {}", id);

        modeloRepository.delete(modelo);
    }

    private ModeloResponseDTO convertirDTO(Modelo modelo) {

        return ModeloResponseDTO.builder()
                .id(modelo.getId())
                .nombreModelo(modelo.getNombreModelo())
                .tipoVehiculo(modelo.getTipoVehiculo())
                .marcaId(modelo.getMarcaId())
                .build();
    }
}