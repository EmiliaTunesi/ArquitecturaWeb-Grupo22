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
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CarreraService {

    private CarreraRepository carreraRepository;




    @Transactional
    public List<CarreraConInscriptosDTO> getAllCarrerasConInscriptos() {
        try {
            return carreraRepository.findCarrerasConCantidadInscriptos();
        } catch (Exception e) {
            System.err.println("Error al recuperar las carreras con inscriptos: " + e.getMessage());
            return List.of();
        }
    }


    @Transactional
    public List<CarreraConInscriptosDTO> getAllCarrerasReporte() {
        try {
            return carreraRepository.getAllCarrerasReporte();
        } catch (Exception e) {
            System.err.println("Error al recuperar el reporte de carreras: " + e.getMessage());
            return List.of();
        }
    }


    public List<CarreraResponseDTO> getCarreras() {
        try {
            List<Carrera> carreras = carreraRepository.findAll();
            return carreras.stream()
                    .map(carrera -> {
                        CarreraResponseDTO dto = new CarreraResponseDTO();
                        dto.setId_carrera(carrera.getId_carrera());
                        dto.setNombre(carrera.getNombre());
                        dto.setDuracion(carrera.getDuracion());
                        return dto;
                    })
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.err.println("Error al recuperar las carreras: " + e.getMessage());
            return List.of();
        }
    }
}
