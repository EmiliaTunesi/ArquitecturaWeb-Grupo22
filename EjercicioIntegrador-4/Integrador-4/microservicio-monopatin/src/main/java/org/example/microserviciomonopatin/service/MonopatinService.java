package org.example.microserviciomonopatin.service;


import org.example.microserviciomonopatin.dto.dtoRequest.ActualizacionMonopatinDTO;
import org.example.microserviciomonopatin.dto.dtoRequest.MonopatinRequestDTO;
import org.example.microserviciomonopatin.dto.dtoResponse.MonopatinResponseDTO;
import org.example.microserviciomonopatin.dto.dtoResponse.ReporteUsoMonopatinDTO;
import org.example.microserviciomonopatin.entity.MonopatinEntity;
import org.example.microserviciomonopatin.exception.ResourceNotFoundException;
import org.example.microserviciomonopatin.repository.MonopatinRepository;
import org.example.microserviciomonopatin.utils.EstadoMonopatin;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Transactional
    public MonopatinResponseDTO marcarFueraDeServicio(String id) {
        MonopatinEntity monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monopatín con ID " + id + " no encontrado"));

        monopatin.setEstado(EstadoMonopatin.FUERA_DE_SERVICIO);
        monopatinRepository.save(monopatin);

        return modelMapper.map(monopatin, MonopatinResponseDTO.class);
    }




    @Transactional
    public MonopatinResponseDTO marcarEnMantenimiento(String id) {
        MonopatinEntity monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monopatín no encontrado con id: " + id));

        monopatin.setEstado(EstadoMonopatin.EN_MANTENIMIENTO);
        MonopatinEntity savedMonopatin = monopatinRepository.save(monopatin);
        return modelMapper.map(savedMonopatin, MonopatinResponseDTO.class);
    }


    @Transactional
    public MonopatinResponseDTO marcarDisponible(String id) {
        MonopatinEntity monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monopatín no encontrado con id: " + id));

        monopatin.setEstado(EstadoMonopatin.DISPONIBLE);
        MonopatinEntity savedMonopatin = monopatinRepository.save(monopatin);
        return modelMapper.map(savedMonopatin, MonopatinResponseDTO.class);
    }


    @Transactional(readOnly = true)
    public MonopatinResponseDTO obtenerMonopatinPorId(String id) {
        MonopatinEntity monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monopatín no encontrado con id: " + id));
        return modelMapper.map(monopatin, MonopatinResponseDTO.class);
    }


    @Transactional(readOnly = true)
    public List<MonopatinResponseDTO> listarTodosLosMonopatines() {
        return monopatinRepository.findAll().stream()
                .map(monopatin -> modelMapper.map(monopatin, MonopatinResponseDTO.class))
                .collect(Collectors.toList());
    }


    @Transactional(readOnly = true)
    public MonopatinEntity obtenerMonopatinEntity(String id) {
        return monopatinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monopatín no encontrado con id: " + id));
    }


    //Límite de km en mantenimiento
    private static final double LIMITE_KM_MANTENIMIENTO = 100.0;

    public List<ReporteUsoMonopatinDTO> generarReporte(boolean incluirPausas) {
        List<MonopatinEntity> monopatines = monopatinRepository.findAll();

        return monopatines.stream().map(m -> {
            boolean requiereMantenimiento =
                    m.getKilometrosTotales() != null && m.getKilometrosTotales() > LIMITE_KM_MANTENIMIENTO;

            Double tiempoPausa = incluirPausas ? m.getTiempoPausaTotal() : null;

            return new ReporteUsoMonopatinDTO(
                    m.getId(),
                    m.getEstado(),
                    m.getKilometrosTotales(),
                    m.getTiempoUsoTotal(),
                    tiempoPausa,
                    requiereMantenimiento
            );
        }).collect(Collectors.toList());


}



    //Me devuelve si el monopatin esta disponible
    @Transactional
    public boolean estaDisponible(String id) {
        MonopatinEntity monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monopatín no encontrado con id: " + id));

        boolean disponible = monopatin.getEstado() == EstadoMonopatin.DISPONIBLE;

        if (disponible) {
            monopatin.setEstado(EstadoMonopatin.EN_USO);
            monopatinRepository.save(monopatin); // Importante
        }

        return disponible;
    }

    @Transactional
    public void finalizarUso(String id, ActualizacionMonopatinDTO dto) {
        MonopatinEntity monopatin = monopatinRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Monopatín no encontrado con id: " + id));

        monopatin.setKilometrosTotales(
                monopatin.getKilometrosTotales() + dto.getKilometrosRecorridos());
        monopatin.setTiempoUsoTotal(
                monopatin.getTiempoUsoTotal() + dto.getTiempoUso());
        monopatin.setTiempoPausaTotal(
                monopatin.getTiempoPausaTotal() + dto.getTiempoPausa());

        monopatin.setEstado(EstadoMonopatin.DISPONIBLE);

        monopatinRepository.save(monopatin);
    }

}


