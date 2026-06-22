package com.automotora.financiamiento_service.repository;

import com.automotora.financiamiento_service.model.Financiamiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FinanciamientoRepository extends JpaRepository<Financiamiento, String> {
    
    // CORREGIDO: Retorna una lista de la entidad Financiamiento filtrada por ventaId
    List<Financiamiento> findByVentaId(String ventaId);
}