package com.automotora.marca_service.repository;

import com.automotora.marca_service.model.Marca;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MarcaRepository extends JpaRepository<Marca, String> {

    boolean existsByNombreMarca(String nombreMarca);
}