package org.example.microserviciomonopatin.service;

import org.example.microserviciomonopatin.dto.dtoRequest.FinalizarMantenimientoRequestDTO;
import org.example.microserviciomonopatin.dto.dtoRequest.IniciarMantenimientoRequestDTO;
import org.example.microserviciomonopatin.dto.dtoResponse.MantenimientoResponseDTO;
import org.example.microserviciomonopatin.entity.MantenimientoEntity;
import org.example.microserviciomonopatin.entity.MonopatinEntity;
import org.example.microserviciomonopatin.exception.ResourceNotFoundException;
import org.example.microserviciomonopatin.repository.MantenimientoRepository;
import org.example.microserviciomonopatin.utils.EstadoMonopatin;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MantenimientoService {

    private final MantenimientoRepository mantenimientoRepository;
    private final MonopatinService monopatinService;
    private final ModelMapper modelMapper;

    public MantenimientoService(MantenimientoRepository mantenimientoRepository,
                                MonopatinService monopatinService,
                                ModelMapper modelMapper) {
        this.mantenimientoRepository = mantenimientoRepository;
        this.monopatinService = monopatinService;
        this.modelMapper = modelMapper;
    }

    @Transactional
    public MantenimientoResponseDTO registrarMantenimiento(IniciarMantenimientoRequestDTO request) {

        // Obtener monopatín (ahora devuelve uno con id String)
        MonopatinEntity monopatin = monopatinService.obtenerMonopatinEntity(request.getMonopatinId());

        // Verificar estado
        if (monopatin.getEstado() == EstadoMonopatin.EN_MANTENIMIENTO) {
            throw new IllegalStateException("El monopatín ya se encuentra en mantenimiento");
        }

        // Verificar mantenimiento activo
        mantenimientoRepository.findByMonopatinIdAndFechaFinIsNull(monopatin.getId())
                .ifPresent(m -> {
                    throw new IllegalStateException("El monopatín ya tiene un mantenimiento activo");
                });

        // Crear mantenimiento (monopatinId es String)
        MantenimientoEntity mantenimiento = new MantenimientoEntity();
        mantenimiento.setMonopatinId(monopatin.getId());
        mantenimiento.setFechaInicio(LocalDate.now());
        mantenimiento.setDescripcion(request.getDescripcion());
        mantenimiento.setTipoMantenimiento(request.getTipoMantenimiento());

        MantenimientoEntity saved = mantenimientoRepository.save(mantenimiento);

        // Cambiar estado del monopatín
        monopatinService.marcarEnMantenimiento(monopatin.getId());

        return convertirAResponseDTO(saved);
    }

    @Transactional
    public MantenimientoResponseDTO finalizarMantenimiento(String mantenimientoId, FinalizarMantenimientoRequestDTO request) {

        MantenimientoEntity mantenimiento = mantenimientoRepository.findById(mantenimientoId)
                .orElseThrow(() -> new ResourceNotFoundException("Mantenimiento no encontrado con id: " + mantenimientoId));

        if (mantenimiento.getFechaFin() != null) {
            throw new IllegalStateException("Este mantenimiento ya ha sido finalizado");
        }

        mantenimiento.setFechaFin(LocalDate.now());

        if (request.getDescripcionFinal() != null && !request.getDescripcionFinal().isEmpty()) {
            String nuevaDesc = mantenimiento.getDescripcion() + " | Finalización: " + request.getDescripcionFinal();
            mantenimiento.setDescripcion(nuevaDesc);
        }

        MantenimientoEntity saved = mantenimientoRepository.save(mantenimiento);

        // Marcar monopatín como disponible
        monopatinService.marcarDisponible(mantenimiento.getMonopatinId());

        return convertirAResponseDTO(saved);
    }

    @Transactional(readOnly = true)
    public MantenimientoResponseDTO obtenerMantenimientoPorId(String id) {
        MantenimientoEntity mantenimiento = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Mantenimiento no encontrado con id: " + id));
        return convertirAResponseDTO(mantenimiento);
    }

    @Transactional(readOnly = true)
    public List<MantenimientoResponseDTO> listarTodosLosMantenimientos() {
        return mantenimientoRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<MantenimientoResponseDTO> listarMantenimientosPorMonopatin(String monopatinId) {
        return mantenimientoRepository.findByMonopatinId(monopatinId).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    private MantenimientoResponseDTO convertirAResponseDTO(MantenimientoEntity mantenimiento) {
        MantenimientoResponseDTO dto = modelMapper.map(mantenimiento, MantenimientoResponseDTO.class);

        // monopatinId ya es String
        dto.setMonopatinId(mantenimiento.getMonopatinId());

        dto.setEstadoMantenimiento(
                mantenimiento.getFechaFin() == null ? "EN_CURSO" : "FINALIZADO"
        );

        return dto;
    }
}
