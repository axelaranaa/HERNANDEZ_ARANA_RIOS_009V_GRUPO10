package com.automotora.usuario_service.service;

import com.automotora.usuario_service.dto.request.UsuarioRequestDTO;
import com.automotora.usuario_service.dto.response.UsuarioResponseDTO;
import com.automotora.usuario_service.exception.UsuarioNotFoundException;
import com.automotora.usuario_service.model.Usuario;
import com.automotora.usuario_service.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<UsuarioResponseDTO> obtenerTodos() {

        log.info("Obteniendo todos los usuarios");

        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public UsuarioResponseDTO obtenerPorId(String id) {

        Usuario usuario = buscarUsuario(id);

        return convertirDTO(usuario);
    }

    public UsuarioResponseDTO guardar(UsuarioRequestDTO dto) {

        Usuario usuario = Usuario.builder()
                .username(dto.getUsername())
                .email(dto.getEmail())
                .password(dto.getPassword())
                .estado(dto.getEstado())
                .rol(dto.getRol())
                .build();

        log.info("Creando usuario {}", dto.getUsername());

        return convertirDTO(usuarioRepository.save(usuario));
    }

    public UsuarioResponseDTO actualizar(String id, UsuarioRequestDTO dto) {

        Usuario usuario = buscarUsuario(id);

        usuario.setUsername(dto.getUsername());
        usuario.setEmail(dto.getEmail());
        usuario.setPassword(dto.getPassword());
        usuario.setEstado(dto.getEstado());
        usuario.setRol(dto.getRol());

        log.info("Actualizando usuario {}", id);

        return convertirDTO(usuarioRepository.save(usuario));
    }

    public void eliminar(String id) {

        Usuario usuario = buscarUsuario(id);

        log.info("Eliminando usuario {}", id);

        usuarioRepository.delete(usuario);
    }

    private Usuario buscarUsuario(String id) {

        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNotFoundException(
                                "Usuario no encontrado con id: " + id));
    }

    private UsuarioResponseDTO convertirDTO(Usuario usuario) {

        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .estado(usuario.getEstado())
                .rol(usuario.getRol())
                .build();
    }
}
