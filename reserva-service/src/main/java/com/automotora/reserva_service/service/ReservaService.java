package com.automotora.reserva_service.service;

import com.automotora.reserva_service.dto.request.ReservaRequestDTO;
import com.automotora.reserva_service.dto.response.ReservaResponseDTO;
import com.automotora.reserva_service.model.Reserva;
import com.automotora.reserva_service.repository.ReservaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservaService {

    private final ReservaRepository reservaRepository;

    public List<ReservaResponseDTO> obtenerTodas() {

        return reservaRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public ReservaResponseDTO obtenerPorId(String id) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        return mapToResponse(reserva);
    }

    public ReservaResponseDTO guardar(ReservaRequestDTO dto) {

        Reserva reserva = Reserva.builder()
                .fechaReserva(dto.getFechaReserva())
                .estadoReserva(dto.getEstadoReserva())
                .clienteId(dto.getClienteId())
                .vehiculoId(dto.getVehiculoId())
                .build();

        Reserva guardada = reservaRepository.save(reserva);

        return mapToResponse(guardada);
    }

    public ReservaResponseDTO actualizar(String id, ReservaRequestDTO dto) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reserva.setFechaReserva(dto.getFechaReserva());
        reserva.setEstadoReserva(dto.getEstadoReserva());
        reserva.setClienteId(dto.getClienteId());
        reserva.setVehiculoId(dto.getVehiculoId());

        Reserva actualizada = reservaRepository.save(reserva);

        return mapToResponse(actualizada);
    }

    public void eliminar(String id) {

        Reserva reserva = reservaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reserva no encontrada"));

        reservaRepository.delete(reserva);
    }

    private ReservaResponseDTO mapToResponse(Reserva reserva) {

        return ReservaResponseDTO.builder()
                .id(reserva.getId())
                .fechaReserva(reserva.getFechaReserva())
                .estadoReserva(reserva.getEstadoReserva())
                .clienteId(reserva.getClienteId())
                .vehiculoId(reserva.getVehiculoId())
                .build();
    }
}