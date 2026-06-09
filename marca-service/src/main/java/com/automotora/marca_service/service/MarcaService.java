package com.automotora.marca_service.service;

import com.automotora.marca_service.dto.request.MarcaRequestDTO;
import com.automotora.marca_service.dto.response.MarcaResponseDTO;
import com.automotora.marca_service.exception.MarcaNotFoundException;
import com.automotora.marca_service.model.Marca;
import com.automotora.marca_service.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MarcaService {

    private final MarcaRepository marcaRepository;

    public List<MarcaResponseDTO> obtenerTodas() {

        log.info("Obteniendo todas las marcas");

        return marcaRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public MarcaResponseDTO obtenerPorId(String id) {

        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() ->
                        new MarcaNotFoundException(
                                "Marca no encontrada"));

        return convertirDTO(marca);
    }

    public MarcaResponseDTO guardar(MarcaRequestDTO dto) {

        Marca marca = Marca.builder()
                .nombreMarca(dto.getNombreMarca())
                .build();

        log.info("Creando marca {}", dto.getNombreMarca());

        return convertirDTO(marcaRepository.save(marca));
    }

    public MarcaResponseDTO actualizar(
            String id,
            MarcaRequestDTO dto) {

        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() ->
                        new MarcaNotFoundException(
                                "Marca no encontrada"));

        marca.setNombreMarca(dto.getNombreMarca());

        log.info("Actualizando marca {}", id);

        return convertirDTO(marcaRepository.save(marca));
    }

    public void eliminar(String id) {

        Marca marca = marcaRepository.findById(id)
                .orElseThrow(() ->
                        new MarcaNotFoundException(
                                "Marca no encontrada"));

        log.info("Eliminando marca {}", id);

        marcaRepository.delete(marca);
    }

    private MarcaResponseDTO convertirDTO(Marca marca) {

        return MarcaResponseDTO.builder()
                .id(marca.getId())
                .nombreMarca(marca.getNombreMarca())
                .build();
    }
}