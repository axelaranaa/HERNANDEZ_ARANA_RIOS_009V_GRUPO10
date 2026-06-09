package com.automotora.usuario_service.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.automotora.usuario_service.model.UsuarioModel;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<UsuarioModel, String> {

    Optional<UsuarioModel> findByUsername(String username);

    Optional<UsuarioModel> findByEmail(String email);

}