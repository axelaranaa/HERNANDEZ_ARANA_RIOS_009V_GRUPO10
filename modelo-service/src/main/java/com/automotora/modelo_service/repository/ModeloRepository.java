package com.automotora.modelo_service.repository;

import com.automotora.modelo_service.model.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModeloRepository extends JpaRepository<Modelo, String> {

    boolean existsByNombreModeloAndMarcaId(
            String nombreModelo,
            String marcaId
    );

}