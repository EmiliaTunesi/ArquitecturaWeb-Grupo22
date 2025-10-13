package com.arquitecturaweb.ejercicioIntegrador3.service;

import com.arquitecturaweb.ejercicioIntegrador3.dto.request.EstudianteRequestDTO;
import com.arquitecturaweb.ejercicioIntegrador3.dto.response.EstudianteResponseDTO;
import com.arquitecturaweb.ejercicioIntegrador3.entity.Estudiante;
import com.arquitecturaweb.ejercicioIntegrador3.repository.EstudianteRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service("EstudianteService")
@AllArgsConstructor
@Transactional(readOnly = true)
public class EstudianteService {

    private EstudianteRepository estudianteRepository;


    @Transactional
    public EstudianteResponseDTO save(EstudianteRequestDTO estudianteDTO) throws Exception {
        try {
            // 1. Convertir el DTO a Entidad
            Estudiante estudiante = new Estudiante();
            estudiante.setNombre(estudianteDTO.getNombre());
            estudiante.setApellido(estudianteDTO.getApellido());
            estudiante.setEdad(estudianteDTO.getEdad());
            estudiante.setDni(estudianteDTO.getDni());
            estudiante.setEmail(estudianteDTO.getEmail());
            estudiante.setGenero(estudianteDTO.getGenero());
            estudiante.setCiudad_residencia(estudianteDTO.getCiudad_residencia());
            estudiante.setLU(estudianteDTO.getLu());

            // 2. Guardar la entidad
            Estudiante estudianteGuardado = estudianteRepository.save(estudiante);

            // 3. Convertir la entidad guardada a DTO de respuesta
            EstudianteResponseDTO responseDTO = new EstudianteResponseDTO();
            responseDTO.setId(estudianteGuardado.getId());
            responseDTO.setNombre(estudianteGuardado.getNombre());
            responseDTO.setApellido(estudianteGuardado.getApellido());
            responseDTO.setEdad(estudianteGuardado.getEdad());
            responseDTO.setDni(estudianteGuardado.getDni());
            responseDTO.setEmail(estudianteGuardado.getEmail());
            responseDTO.setGenero(estudianteGuardado.getGenero());
            responseDTO.setCiudad_residencia(estudianteGuardado.getCiudad_residencia());
            responseDTO.setLU(estudianteGuardado.getLU());




            return responseDTO;
        } catch (Exception e) {
            throw new Exception("Error al guardar el estudiante: " + e.getMessage());
        }
    }



}
