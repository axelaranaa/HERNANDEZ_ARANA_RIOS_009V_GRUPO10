package com.automotora.venta_service.repository;

import com.automotora.venta_service.model.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, String> {
    // Al heredar de JpaRepository<Venta, String>, Spring Boot maneja automáticamente
    // todas las operaciones CRUD usando la entidad Venta y su ID de tipo String (UUID).
}