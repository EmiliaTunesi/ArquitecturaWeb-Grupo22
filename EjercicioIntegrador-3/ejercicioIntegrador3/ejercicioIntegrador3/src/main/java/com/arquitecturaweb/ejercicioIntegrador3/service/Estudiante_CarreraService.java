package com.arquitecturaweb.ejercicioIntegrador3.service;


import com.arquitecturaweb.ejercicioIntegrador3.dto.request.Estudiante_CarreraRequestDTO;
import com.arquitecturaweb.ejercicioIntegrador3.dto.response.Estudiante_CarreraResponseDTO;
import com.arquitecturaweb.ejercicioIntegrador3.entity.Carrera;
import com.arquitecturaweb.ejercicioIntegrador3.entity.Estudiante;
import com.arquitecturaweb.ejercicioIntegrador3.entity.Estudiante_Carrera;
import com.arquitecturaweb.ejercicioIntegrador3.repository.CarreraRepository;
import com.arquitecturaweb.ejercicioIntegrador3.repository.EstudianteRepository;
import com.arquitecturaweb.ejercicioIntegrador3.repository.Estudiante_CarreraRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class Estudiante_CarreraService {

    private final Estudiante_CarreraRepository estudianteCarreraRepository;
    private final EstudianteRepository estudianteRepository;
    private final CarreraRepository carreraRepository;

    @Transactional
    public Estudiante_CarreraResponseDTO save(Estudiante_CarreraRequestDTO dto) throws Exception {
        try {
            Estudiante estudiante = estudianteRepository.findByDni(dto.getDni())
                    .orElseThrow(() -> new Exception("Estudiante no encontrado con id: " + dto.getDni()));

            Carrera carrera = carreraRepository.findById(dto.getCarreraId())
                    .orElseThrow(() -> new Exception("Carrera no encontrada con id: " + dto.getCarreraId()));

            Estudiante_Carrera estudianteCarrera = new Estudiante_Carrera(estudiante,carrera);
            estudianteCarrera.setAnio_inicio(dto.getAnio_inicio());
            estudianteCarrera.setAnio_fin(dto.getAnio_fin());

            estudianteCarrera.setAntiguedad(dto.getAntiguedad());
            Estudiante_Carrera guardado = estudianteCarreraRepository.save(estudianteCarrera);

            return convertToDTO(guardado);
        } catch (Exception e) {
            throw new Exception("Error al inscribir estudiante en carrera: " + e.getMessage());
        }
    }


    // Métodos helper
    private Estudiante_CarreraResponseDTO convertToDTO(Estudiante_Carrera ec) {
        Estudiante_CarreraResponseDTO dto = new Estudiante_CarreraResponseDTO();
        dto.setId(ec.getId());
        dto.setEstudianteId(ec.getEstudiante().getId());
        dto.setCarreraId(ec.getCarrera().getId_carrera());
        dto.setAnio_inicio(ec.getAnio_inicio());
        dto.setAnio_fin(ec.getAnio_fin());
        dto.setAntiguedad(ec.getAntiguedad());
        return dto;
    }


}
