package com.automotora.cliente_service.service;

import com.automotora.cliente_service.dto.request.ClienteRequestDTO;
import com.automotora.cliente_service.dto.response.ClienteResponseDTO;
import com.automotora.cliente_service.exception.ClienteNotFoundException;
import com.automotora.cliente_service.model.Cliente;
import com.automotora.cliente_service.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;

    public List<ClienteResponseDTO> obtenerTodos() {

        log.info("Obteniendo todos los clientes");

        return clienteRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public ClienteResponseDTO obtenerPorId(String id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ClienteNotFoundException(
                                "Cliente no encontrado"));

        return convertirDTO(cliente);
    }

    public ClienteResponseDTO guardar(ClienteRequestDTO dto) {

        Cliente cliente = Cliente.builder()
                .rut(dto.getRut())
                .dv(dto.getDv())
                .nombre(dto.getNombre())
                .apellido(dto.getApellido())
                .email(dto.getEmail())
                .telefono(dto.getTelefono())
                .direccion(dto.getDireccion())
                .fechaRegistro(dto.getFechaRegistro())
                .estado(dto.getEstado())
                .usuarioId(dto.getUsuarioId())
                .build();

        log.info("Creando cliente {}", dto.getRut());

        return convertirDTO(clienteRepository.save(cliente));
    }

    public ClienteResponseDTO actualizar(
            String id,
            ClienteRequestDTO dto) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ClienteNotFoundException(
                                "Cliente no encontrado"));

        cliente.setRut(dto.getRut());
        cliente.setDv(dto.getDv());
        cliente.setNombre(dto.getNombre());
        cliente.setApellido(dto.getApellido());
        cliente.setEmail(dto.getEmail());
        cliente.setTelefono(dto.getTelefono());
        cliente.setDireccion(dto.getDireccion());
        cliente.setFechaRegistro(dto.getFechaRegistro());
        cliente.setEstado(dto.getEstado());
        cliente.setUsuarioId(dto.getUsuarioId());

        log.info("Actualizando cliente {}", id);

        return convertirDTO(clienteRepository.save(cliente));
    }

    public void eliminar(String id) {

        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ClienteNotFoundException(
                                "Cliente no encontrado"));

        log.info("Eliminando cliente {}", id);

        clienteRepository.delete(cliente);
    }

    private ClienteResponseDTO convertirDTO(Cliente cliente) {

        return ClienteResponseDTO.builder()
                .id(cliente.getId())
                .rut(cliente.getRut())
                .dv(cliente.getDv())
                .nombre(cliente.getNombre())
                .apellido(cliente.getApellido())
                .email(cliente.getEmail())
                .telefono(cliente.getTelefono())
                .direccion(cliente.getDireccion())
                .fechaRegistro(cliente.getFechaRegistro())
                .estado(cliente.getEstado())
                .usuarioId(cliente.getUsuarioId())
                .build();
    }
}