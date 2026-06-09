package com.automotora.vehiculo_service.repository;

import com.automotora.vehiculo_service.model.Vehiculo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VehiculoRepository extends JpaRepository<Vehiculo, String> {
}