package com.automotora.pago_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.automotora.pago_service.model.Pago;

@Repository
public interface PagoRepository extends JpaRepository<Pago, String> {
}
