package com.arquitecturaweb.ejercicioIntegrador3.service;

import com.arquitecturaweb.ejercicioIntegrador3.dto.request.EstudianteRequestDTO;
import com.arquitecturaweb.ejercicioIntegrador3.dto.response.EstudianteResponseDTO;
import com.arquitecturaweb.ejercicioIntegrador3.entity.Estudiante;
import com.arquitecturaweb.ejercicioIntegrador3.repository.EstudianteRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


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
            responseDTO.setGenero(estudianteGuardado.getGenero());
            responseDTO.setCiudad_residencia(estudianteGuardado.getCiudad_residencia());
            responseDTO.setLU(estudianteGuardado.getLU());




            return responseDTO;
        } catch (Exception e) {
            throw new Exception("Error al guardar el estudiante: " + e.getMessage());
        }
    }

    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> getAllEstudiantesPorGenero(String genero){
        try {
            List<Estudiante> estudiantes = estudianteRepository.findByGeneroIgnoreCase(genero);
            return estudiantes.stream().map(estudiante -> {
                EstudianteResponseDTO dto = new EstudianteResponseDTO();
                dto.setId(estudiante.getId());
                dto.setNombre(estudiante.getNombre());
                dto.setApellido(estudiante.getApellido());
                dto.setEdad(estudiante.getEdad());
                dto.setDni(estudiante.getDni());
                dto.setGenero(estudiante.getGenero());
                dto.setCiudad_residencia(estudiante.getCiudad_residencia());
                dto.setLU(estudiante.getLU());
                return dto;
            }).toList();
        }catch (Exception e){
            System.err.println("Error al recuperar los estudiantes por genero: " + e.getMessage());
            // Podés devolver una lista vacía o relanzar la excepción, según el caso
            return List.of(); // devuelve lista vacía si algo falla
        }
    }
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> getAllEstudiantesPorApellido() {
        try {
            // Ordenar por apellido ascendente
            List<Estudiante> estudiantes = estudianteRepository.findAll(Sort.by(Sort.Direction.ASC, "apellido"));

            // Convertir a lista de DTOs de respuesta
            return estudiantes.stream().map(estudiante -> {
                EstudianteResponseDTO dto = new EstudianteResponseDTO();
                dto.setId(estudiante.getId());
                dto.setNombre(estudiante.getNombre());
                dto.setApellido(estudiante.getApellido());
                dto.setEdad(estudiante.getEdad());
                dto.setDni(estudiante.getDni());
                dto.setGenero(estudiante.getGenero());
                dto.setCiudad_residencia(estudiante.getCiudad_residencia());
                dto.setLU(estudiante.getLU());
                return dto;
            }).toList();

        } catch (Exception e) {
            System.err.println("Error al recuperar los estudiantes: " + e.getMessage());
            // Podés devolver una lista vacía o relanzar la excepción, según el caso
            return List.of(); // devuelve lista vacía si algo falla
        }
    }


    @Transactional(readOnly = true)
    public EstudianteResponseDTO getEstudianteByLU(int lu) throws Exception {
        try {
            // Buscar estudiante por LU
            Estudiante estudiante = estudianteRepository.findByLU(lu)
                    .orElseThrow(() -> new Exception("Estudiante con LU " + lu + " no encontrado."));

            // Convertir la entidad a DTO de respuesta
            EstudianteResponseDTO dto = new EstudianteResponseDTO();
            dto.setId(estudiante.getId());
            dto.setNombre(estudiante.getNombre());
            dto.setApellido(estudiante.getApellido());
            dto.setEdad(estudiante.getEdad());
            dto.setDni(estudiante.getDni());
            dto.setGenero(estudiante.getGenero());
            dto.setCiudad_residencia(estudiante.getCiudad_residencia());
            dto.setLU(estudiante.getLU());

            return dto;

        } catch (Exception e) {
            System.err.println("Error al recuperar estudiante por LU: " + e.getMessage());
            throw new Exception("No se pudo recuperar el estudiante con LU " + lu + ": " + e.getMessage());
        }
    }
    //g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
    @Transactional(readOnly = true)
    public List<EstudianteResponseDTO> findByCarreraCiudad(int idCarrera, String ciudad){
        try {
            List<Estudiante> estudiantes = estudianteRepository.findByCarreraCiudad(idCarrera, ciudad);
            return estudiantes.stream().map(estudiante -> {
                EstudianteResponseDTO dto = new EstudianteResponseDTO();
                dto.setId(estudiante.getId());
                dto.setNombre(estudiante.getNombre());
                dto.setApellido(estudiante.getApellido());
                dto.setEdad(estudiante.getEdad());
                dto.setDni(estudiante.getDni());
                dto.setGenero(estudiante.getGenero());
                dto.setCiudad_residencia(estudiante.getCiudad_residencia());
                dto.setLU(estudiante.getLU());
                return dto;
            }).toList();
        } catch (Exception e) {
            System.err.println("Error al recuperar estudiante por carrera y ciudad: " + e.getMessage());
            return List.of(); // devuelve lista vacía si algo falla
        }
    }

}
