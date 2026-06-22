package com.automotora.cliente_service.service;

import com.automotora.cliente_service.client.UsuarioClient;
import com.automotora.cliente_service.dto.request.ClienteRequestDTO;
import com.automotora.cliente_service.dto.response.ClienteResponseDTO;
import com.automotora.cliente_service.dto.response.UsuarioResponseDTO;
import com.automotora.cliente_service.exception.ClienteNotFoundException;
import com.automotora.cliente_service.exception.RecursoRelacionadoNoEncontradoException;
import com.automotora.cliente_service.exception.ServicioExternoNoDisponibleException;
import com.automotora.cliente_service.model.Cliente;
import com.automotora.cliente_service.repository.ClienteRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final UsuarioClient usuarioClient;

    public List<ClienteResponseDTO> obtenerTodos() {

        log.info("Obteniendo todos los clientes");

        return clienteRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public ClienteResponseDTO obtenerPorId(String id) {

        Cliente cliente = buscarCliente(id);

        return convertirDTO(cliente);
    }

    public ClienteResponseDTO guardar(ClienteRequestDTO dto) {

        log.info("Validando existencia del usuario {}", dto.getUsuarioId());

        validarUsuarioExiste(dto.getUsuarioId());

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

        log.info("Guardando cliente {}", dto.getRut());

        return convertirDTO(clienteRepository.save(cliente));
    }

    public ClienteResponseDTO actualizar(String id, ClienteRequestDTO dto) {

        Cliente cliente = buscarCliente(id);

        validarUsuarioExiste(dto.getUsuarioId());

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

        Cliente cliente = buscarCliente(id);

        log.info("Eliminando cliente {}", id);

        clienteRepository.delete(cliente);
    }

    private void validarUsuarioExiste(String usuarioId) {

        try {
            UsuarioResponseDTO usuario = usuarioClient.obtenerUsuario(usuarioId);

            if (usuario == null) {
                throw new RecursoRelacionadoNoEncontradoException(
                        "El usuario con id " + usuarioId + " no existe");
            }

        } catch (FeignException.NotFound e) {
            throw new RecursoRelacionadoNoEncontradoException(
                    "El usuario con id " + usuarioId + " no existe");

        } catch (FeignException e) {
            log.error("Error al consultar usuario-service: {}", e.getMessage());
            throw new ServicioExternoNoDisponibleException(
                    "No se pudo validar el usuario, el servicio no está disponible");
        }
    }

    private Cliente buscarCliente(String id) {

        return clienteRepository.findById(id)
                .orElseThrow(() ->
                        new ClienteNotFoundException(
                                "Cliente no encontrado con id: " + id));
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
