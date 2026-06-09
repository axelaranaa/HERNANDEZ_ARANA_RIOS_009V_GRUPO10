package com.automotora.usuario_service.service;

import com.automotora.usuario_service.dto.request.UsuarioRequestDTO;
import com.automotora.usuario_service.dto.response.UsuarioResponseDTO;
import com.automotora.usuario_service.exception.UsuarioNotFoundException;
import com.automotora.usuario_service.model.UsuarioModel;
import com.automotora.usuario_service.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public List<UsuarioResponseDTO> obtenerTodos() {

        return usuarioRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public UsuarioResponseDTO obtenerPorId(String id) {

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNotFoundException(
                                "Usuario no encontrado"));

        return convertirDTO(usuario);
    }

    public UsuarioResponseDTO crearUsuario(
            UsuarioRequestDTO request) {

        UsuarioModel usuario = UsuarioModel.builder()
                .username(request.getUsername())
                .email(request.getEmail())
                .password(request.getPassword())
                .estado(request.getEstado())
                .rol(request.getRol())
                .build();

        return convertirDTO(
                usuarioRepository.save(usuario));
    }

    public UsuarioResponseDTO actualizarUsuario(
            String id,
            UsuarioRequestDTO request) {

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNotFoundException(
                                "Usuario no encontrado"));

        usuario.setUsername(request.getUsername());
        usuario.setEmail(request.getEmail());
        usuario.setPassword(request.getPassword());
        usuario.setEstado(request.getEstado());
        usuario.setRol(request.getRol());

        return convertirDTO(
                usuarioRepository.save(usuario));
    }

    public void eliminarUsuario(String id) {

        UsuarioModel usuario = usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new UsuarioNotFoundException(
                                "Usuario no encontrado"));

        usuarioRepository.delete(usuario);
    }

    private UsuarioResponseDTO convertirDTO(
            UsuarioModel usuario) {

        return UsuarioResponseDTO.builder()
                .id(usuario.getId())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .estado(usuario.getEstado())
                .rol(usuario.getRol())
                .build();
    }
}