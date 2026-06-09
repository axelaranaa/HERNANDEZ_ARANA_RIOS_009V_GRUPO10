package com.automotora.cliente_service.repository;

import com.automotora.cliente_service.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository
        extends JpaRepository<Cliente, String> {

    boolean existsByRut(String rut);

    boolean existsByEmail(String email);
}