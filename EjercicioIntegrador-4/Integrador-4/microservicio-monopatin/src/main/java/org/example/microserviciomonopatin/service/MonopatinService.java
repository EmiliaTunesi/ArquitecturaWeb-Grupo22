package org.example.microserviciomonopatin.service;


import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.example.microserviciomonopatin.dto.dtoRequest.MonopatinRequestDTO;
import org.example.microserviciomonopatin.dto.dtoRequest.UbicarMonopatinRequestDTO;
import org.example.microserviciomonopatin.dto.dtoResponse.MonopatinResponseDTO;
import org.example.microserviciomonopatin.entity.MonopatinEntity;
import org.example.microserviciomonopatin.exception.ResourceNotFoundException;
import org.example.microserviciomonopatin.repository.MonopatinRepository;
import org.example.microserviciomonopatin.utils.EstadoMonopatin;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MonopatinService {

    private final MonopatinRepository monopatinRepository;
    private final ModelMapper modelMapper;

    public MonopatinService(MonopatinRepository monopatinRepository, ModelMapper modelMapper) {
        this.monopatinRepository = monopatinRepository;
        this.modelMapper = modelMapper;
    }


    @Transactional
    public MonopatinResponseDTO agregarMonopatin(MonopatinRequestDTO request) {
        MonopatinEntity monopatin = modelMapper.map(request, MonopatinEntity.class);
        monopatin.setEstado(EstadoMonopatin.DISPONIBLE);
        monopatin.setKilometrosTotales(0.0);
        monopatin.setTiempoUsoTotal(0.0);
        monopatin.setTiempoPausaTotal(0.0);
        monopatin.setFechaAlta(LocalDate.now());

        MonopatinEntity savedMonopatin = monopatinRepository.save(monopatin);
        return modelMapper.map(savedMonopatin, MonopatinResponseDTO.class);
    }

    /**
     * Quitar/eliminar monopatín
     */
    @Transactional
    public MonopatinResponseDTO marcarFueraDeServicio(Long id) {
        MonopatinEntity monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monopatín con ID " + id + " no encontrado"));

        monopatin.setEstado(EstadoMonopatin.FUERA_DE_SERVICIO);
        monopatinRepository.save(monopatin);

        return modelMapper.map(monopatin, MonopatinResponseDTO.class);
    }




    /**
     * Marcar monopatín como en mantenimiento
     */
    @Transactional
    public MonopatinResponseDTO marcarEnMantenimiento(Long id) {
        MonopatinEntity monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monopatín no encontrado con id: " + id));

        monopatin.setEstado(EstadoMonopatin.EN_MANTENIMIENTO);
        MonopatinEntity savedMonopatin = monopatinRepository.save(monopatin);
        return modelMapper.map(savedMonopatin, MonopatinResponseDTO.class);
    }

    /**
     * Marcar monopatín como disponible (fin de mantenimiento)
     */
    @Transactional
    public MonopatinResponseDTO marcarDisponible(Long id) {
        MonopatinEntity monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monopatín no encontrado con id: " + id));

        monopatin.setEstado(EstadoMonopatin.DISPONIBLE);
        MonopatinEntity savedMonopatin = monopatinRepository.save(monopatin);
        return modelMapper.map(savedMonopatin, MonopatinResponseDTO.class);
    }

    /**
     * Obtener monopatín por ID
     */
    @Transactional(readOnly = true)
    public MonopatinResponseDTO obtenerMonopatinPorId(Long id) {
        MonopatinEntity monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monopatín no encontrado con id: " + id));
        return modelMapper.map(monopatin, MonopatinResponseDTO.class);
    }

    /**
     * Listar todos los monopatines
     */
    @Transactional(readOnly = true)
    public List<MonopatinResponseDTO> listarTodosLosMonopatines() {
        return monopatinRepository.findAll().stream()
                .map(monopatin -> modelMapper.map(monopatin, MonopatinResponseDTO.class))
                .collect(Collectors.toList());
    }

    /**
     * Obtener entidad de monopatín (para uso interno)
     */
    @Transactional(readOnly = true)
    public MonopatinEntity obtenerMonopatinEntity(Long id) {
        return monopatinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monopatín no encontrado con id: " + id));
    }
    }


