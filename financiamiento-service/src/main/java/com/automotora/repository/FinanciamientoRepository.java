package com.automotora.repository; 
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.automotora.model.Financiamiento; 

@Repository
public interface FinanciamientoRepository extends JpaRepository<Financiamiento, String> { 
}