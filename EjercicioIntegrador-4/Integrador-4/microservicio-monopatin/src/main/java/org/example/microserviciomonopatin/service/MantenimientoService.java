package org.example.microserviciomonopatin.service;


import jakarta.persistence.EntityNotFoundException;
import org.example.microserviciomonopatin.dto.dtoRequest.FinalizarMantenimientoRequestDTO;
import org.example.microserviciomonopatin.dto.dtoRequest.IniciarMantenimientoRequestDTO;
import org.example.microserviciomonopatin.dto.dtoResponse.MantenimientoResponseDTO;
import org.example.microserviciomonopatin.entity.MantenimientoEntity;
import org.example.microserviciomonopatin.entity.MonopatinEntity;
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

    /**
     * Registrar monopatín en mantenimiento
     */
    @Transactional
    public MantenimientoResponseDTO registrarMantenimiento(IniciarMantenimientoRequestDTO request) {
        // Obtener el monopatín
        MonopatinEntity monopatin = monopatinService.obtenerMonopatinEntity(request.getMonopatinId());

        // Verificar que no esté ya en mantenimiento
        if (monopatin.getEstado() == EstadoMonopatin.EN_MANTENIMIENTO) {
            throw new IllegalStateException("El monopatín ya se encuentra en mantenimiento");
        }

        // Verificar que no tenga un mantenimiento activo
        mantenimientoRepository.findByMonopatinIdAndFechaFinIsNull(monopatin)
                .ifPresent(m -> {
                    throw new IllegalStateException("El monopatín ya tiene un mantenimiento activo");
                });

        // Crear el registro de mantenimiento (mapeo manual porque ModelMapper se confunde con los IDs)
        MantenimientoEntity mantenimiento = new MantenimientoEntity();
        mantenimiento.setMonopatinId(monopatin);
        mantenimiento.setFechaInicio(LocalDate.now());
        mantenimiento.setDescripcion(request.getDescripcion());
        mantenimiento.setTipoMantenimiento(request.getTipoMantenimiento());

        MantenimientoEntity savedMantenimiento = mantenimientoRepository.save(mantenimiento);

        // Marcar el monopatín como en mantenimiento
        monopatinService.marcarEnMantenimiento(monopatin.getId());

        return convertirAResponseDTO(savedMantenimiento);
    }

    /**
     * Registrar fin de mantenimiento
     */
    @Transactional
    public MantenimientoResponseDTO finalizarMantenimiento(Long mantenimientoId, FinalizarMantenimientoRequestDTO request) {
        // Obtener el mantenimiento
        MantenimientoEntity mantenimiento = mantenimientoRepository.findById(mantenimientoId)
                .orElseThrow(() -> new EntityNotFoundException("Mantenimiento no encontrado con id: " + mantenimientoId));

        // Verificar que no esté ya finalizado
        if (mantenimiento.getFechaFin() != null) {
            throw new IllegalStateException("Este mantenimiento ya ha sido finalizado");
        }

        // Actualizar el mantenimiento
        mantenimiento.setFechaFin(LocalDate.now());
        if (request.getDescripcionFinal() != null && !request.getDescripcionFinal().isEmpty()) {
            String descripcionActualizada = mantenimiento.getDescripcion() + " | Finalización: " + request.getDescripcionFinal();
            mantenimiento.setDescripcion(descripcionActualizada);
        }

        MantenimientoEntity savedMantenimiento = mantenimientoRepository.save(mantenimiento);

        // Marcar el monopatín como disponible
        monopatinService.marcarDisponible(mantenimiento.getMonopatinId().getId());

        return convertirAResponseDTO(savedMantenimiento);
    }

    @Transactional(readOnly = true)
    public MantenimientoResponseDTO obtenerMantenimientoPorId(Long id) {
        MantenimientoEntity mantenimiento = mantenimientoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mantenimiento no encontrado con id: " + id));
        return convertirAResponseDTO(mantenimiento);
    }

    /**
     * Listar todos los mantenimientos
     */
    @Transactional(readOnly = true)
    public List<MantenimientoResponseDTO> listarTodosLosMantenimientos() {
        return mantenimientoRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Listar mantenimientos de un monopatín específico
     */
    @Transactional(readOnly = true)
    public List<MantenimientoResponseDTO> listarMantenimientosPorMonopatin(Long monopatinId) {
        MonopatinEntity monopatin = monopatinService.obtenerMonopatinEntity(monopatinId);
        return mantenimientoRepository.findByMonopatinId(monopatin).stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Método auxiliar para convertir a DTO con el estado del mantenimiento
     */
    private MantenimientoResponseDTO convertirAResponseDTO(MantenimientoEntity mantenimiento) {
        MantenimientoResponseDTO dto = modelMapper.map(mantenimiento, MantenimientoResponseDTO.class);

        // Establecer el monopatinId manualmente porque viene de una relación
        dto.setMonopatinId(mantenimiento.getMonopatinId().getId());

        // Calcular el estado del mantenimiento
        String estadoMantenimiento = mantenimiento.getFechaFin() == null ? "EN_CURSO" : "FINALIZADO";
        dto.setEstadoMantenimiento(estadoMantenimiento);

        return dto;
    }
}
