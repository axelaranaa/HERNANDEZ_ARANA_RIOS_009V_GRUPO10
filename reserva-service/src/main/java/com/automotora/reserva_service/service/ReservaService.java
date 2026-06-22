package com.automotora.reserva_service.service;

import com.automotora.reserva_service.client.ClienteClient;
import com.automotora.reserva_service.client.VehiculoClient;
import com.automotora.reserva_service.dto.request.ReservaRequestDTO;
import com.automotora.reserva_service.dto.response.ClienteResponseDTO;
import com.automotora.reserva_service.dto.response.ReservaResponseDTO;
import com.automotora.reserva_service.dto.response.VehiculoResponseDTO;
import com.automotora.reserva_service.exception.RecursoRelacionadoNoEncontradoException;
import com.automotora.reserva_service.exception.ReservaNotFoundException;
import com.automotora.reserva_service.exception.ServicioExternoNoDisponibleException;
import com.automotora.reserva_service.model.Reserva;
import com.automotora.reserva_service.repository.ReservaRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservaService {

    private final ReservaRepository reservaRepository;
    private final ClienteClient clienteClient;
    private final VehiculoClient vehiculoClient;

    public List<ReservaResponseDTO> obtenerTodas() {

        log.info("Obteniendo todas las reservas");

        return reservaRepository.findAll()
                .stream()
                .map(this::convertirDTO)
                .toList();
    }

    public ReservaResponseDTO obtenerPorId(String id) {

        Reserva reserva = buscarReserva(id);

        return convertirDTO(reserva);
    }

    public ReservaResponseDTO guardar(ReservaRequestDTO dto) {

        log.info("Validando cliente {} y vehículo {}", dto.getClienteId(), dto.getVehiculoId());

        validarClienteExiste(dto.getClienteId());
        validarVehiculoExiste(dto.getVehiculoId());

        Reserva reserva = Reserva.builder()
                .fechaReserva(dto.getFechaReserva())
                .estadoReserva(dto.getEstadoReserva())
                .clienteId(dto.getClienteId())
                .vehiculoId(dto.getVehiculoId())
                .build();

        log.info("Guardando reserva");

        return convertirDTO(reservaRepository.save(reserva));
    }

    public ReservaResponseDTO actualizar(String id, ReservaRequestDTO dto) {

        Reserva reserva = buscarReserva(id);

        validarClienteExiste(dto.getClienteId());
        validarVehiculoExiste(dto.getVehiculoId());

        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setEstadoReserva(dto.getEstadoReserva());
        reserva.setClienteId(dto.getClienteId());
        reserva.setVehiculoId(dto.getVehiculoId());

        log.info("Actualizando reserva {}", id);

        return convertirDTO(reservaRepository.save(reserva));
    }

    public void eliminar(String id) {

        Reserva reserva = buscarReserva(id);

        log.info("Eliminando reserva {}", id);

        reservaRepository.delete(reserva);
    }

    private void validarClienteExiste(String clienteId) {

        try {
            ClienteResponseDTO cliente = clienteClient.obtenerCliente(clienteId);

            if (cliente == null) {
                throw new RecursoRelacionadoNoEncontradoException(
                        "El cliente con id " + clienteId + " no existe");
            }

        } catch (FeignException.NotFound e) {
            throw new RecursoRelacionadoNoEncontradoException(
                    "El cliente con id " + clienteId + " no existe");

        } catch (FeignException e) {
            log.error("Error al consultar cliente-service: {}", e.getMessage());
            throw new ServicioExternoNoDisponibleException(
                    "No se pudo validar el cliente, el servicio no está disponible");
        }
    }

    private void validarVehiculoExiste(String vehiculoId) {

        try {
            VehiculoResponseDTO vehiculo = vehiculoClient.obtenerVehiculo(vehiculoId);

            if (vehiculo == null) {
                throw new RecursoRelacionadoNoEncontradoException(
                        "El vehículo con id " + vehiculoId + " no existe");
            }

        } catch (FeignException.NotFound e) {
            throw new RecursoRelacionadoNoEncontradoException(
                    "El vehículo con id " + vehiculoId + " no existe");

        } catch (FeignException e) {
            log.error("Error al consultar vehiculo-service: {}", e.getMessage());
            throw new ServicioExternoNoDisponibleException(
                    "No se pudo validar el vehículo, el servicio no está disponible");
        }
    }

    private Reserva buscarReserva(String id) {

        return reservaRepository.findById(id)
                .orElseThrow(() ->
                        new ReservaNotFoundException(
                                "Reserva no encontrada con id: " + id));
    }

    private ReservaResponseDTO convertirDTO(Reserva reserva) {

        return ReservaResponseDTO.builder()
                .id(reserva.getId())
                .fechaReserva(reserva.getFechaReserva())
                .estadoReserva(reserva.getEstadoReserva())
                .clienteId(reserva.getClienteId())
                .vehiculoId(reserva.getVehiculoId())
                .build();
    }
}
