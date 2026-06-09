package com.automotora.servicio_tecnico_service.repository;

import com.automotora.servicio_tecnico_service.model.ServicioTecnico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicioTecnicoRepository
        extends JpaRepository<ServicioTecnico, String> {
}