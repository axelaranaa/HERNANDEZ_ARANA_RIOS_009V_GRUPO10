package com.automotora.reserva_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.automotora.reserva_service.model.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, String> {
}