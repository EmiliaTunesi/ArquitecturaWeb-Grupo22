package com.arquitecturaweb.ejercicioIntegrador3.service;


import com.arquitecturaweb.ejercicioIntegrador3.dto.request.CarreraRequestDTO;
import com.arquitecturaweb.ejercicioIntegrador3.dto.response.CarreraConInscriptosDTO;
import com.arquitecturaweb.ejercicioIntegrador3.dto.response.CarreraResponseDTO;
import com.arquitecturaweb.ejercicioIntegrador3.dto.response.EstudianteResponseDTO;
import com.arquitecturaweb.ejercicioIntegrador3.entity.Carrera;
import com.arquitecturaweb.ejercicioIntegrador3.entity.Estudiante;
import com.arquitecturaweb.ejercicioIntegrador3.repository.CarreraRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CarreraService {

    private CarreraRepository carreraRepository;


    @Transactional
    public CarreraResponseDTO save(CarreraRequestDTO carreraDTO) throws Exception {
        try {
            // Convertir DTO a Entidad
            Carrera carrera = new Carrera();
            carrera.setNombre(carreraDTO.getNombre());
            carrera.setDuracion(carreraDTO.getDuracion());

            // Guardar
            Carrera carreraGuardada = carreraRepository.save(carrera);

            // Convertir Entidad a DTO de respuesta
            CarreraResponseDTO responseDTO = new CarreraResponseDTO();
            responseDTO.setId_carrera(carreraGuardada.getId_carrera());
            responseDTO.setNombre(carreraGuardada.getNombre());
            responseDTO.setDuracion(carreraGuardada.getDuracion());

            return responseDTO;
        } catch (Exception e) {
            throw new Exception("Error al guardar la carrera: " + e.getMessage());
        }
    }
    @Transactional
    public List<CarreraConInscriptosDTO> getAllCarrerasConInscriptos() {
        try {
            return carreraRepository.findCarrerasConCantidadInscriptos();
        } catch (Exception e) {
            System.err.println("Error al recuperar las carreras con inscriptos: " + e.getMessage());
            return List.of();
        }
    }
    /*
    @Transactional
    public List<CarreraConInscriptosDTO> getAllCarrerasConInscriptos(){
        try{
            List<CarreraConInscriptosDTO> carreraResp = carreraRepository.findAll();
            //convertir a lista DTO
            return carreraResp.stream().map(carreraResp-> {
                CarreraConInscriptosDTO dto = new CarreraConInscriptosDTO();
                dto.setCarreraId(carreraResp.getCarreraId());
                dto.setNombreCarrera(carreraResp.getNombreCarrera());
                dto.setCantidadInscriptos(carreraResp.getCantidadInscriptos());
                return dto;
            }).toList();
        } catch (Exception e) {
            System.err.println("Error al recuperar las carreras con inscriptos: " + e.getMessage());
            return List.of(); // devuelve lista vacía si algo falla
        }
    }*/
}
