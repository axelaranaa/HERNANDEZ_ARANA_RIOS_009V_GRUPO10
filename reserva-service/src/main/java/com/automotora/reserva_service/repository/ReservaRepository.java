package com.automotora.reserva_service.repository;

import com.automotora.reserva_service.model.Reserva;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservaRepository extends JpaRepository<Reserva, String> {
}
