package automotora.financiamiento_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import automotora.financiamiento_service.model.Financiamiento;

public interface FinanciamientoRepository
        extends JpaRepository<Financiamiento, String> {
}