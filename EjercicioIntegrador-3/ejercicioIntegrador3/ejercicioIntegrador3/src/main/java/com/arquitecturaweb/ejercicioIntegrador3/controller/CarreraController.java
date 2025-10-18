package com.arquitecturaweb.ejercicioIntegrador3.controller;


import com.arquitecturaweb.ejercicioIntegrador3.dto.request.CarreraRequestDTO;
import com.arquitecturaweb.ejercicioIntegrador3.dto.response.CarreraConInscriptosDTO;
import com.arquitecturaweb.ejercicioIntegrador3.dto.response.CarreraResponseDTO;
import com.arquitecturaweb.ejercicioIntegrador3.dto.response.EstudianteResponseDTO;
import com.arquitecturaweb.ejercicioIntegrador3.service.CarreraService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/carreras")
@AllArgsConstructor
public class CarreraController {

    private CarreraService carreraService;

    @PostMapping
    public ResponseEntity<CarreraResponseDTO> createCarrera(
            @Valid @RequestBody CarreraRequestDTO carreraDTO) {
        try {
            CarreraResponseDTO carrera = carreraService.save(carreraDTO);
            return new ResponseEntity<>(carrera, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException("Error al crear la carrera", e);
        }
    }
    //f) recuperar las carreras con estudiantes inscriptos,
    // y ordenar por cantidad de inscriptos.
    @GetMapping("/con-inscriptos")
    public ResponseEntity<List<CarreraConInscriptosDTO>> getAllCarrerasConInscriptos() {
        List<CarreraConInscriptosDTO> carrerasInscrip = carreraService.getAllCarrerasConInscriptos();
        return ResponseEntity.ok(carrerasInscrip);
    }

    /*h)generar un reporte de las carreras, que para cada carrera incluya
        información de los inscriptos y egresados por año. Se deben ordenar las carreras
        alfabéticamente, y presentar los años de manera cronológica*/
    @GetMapping("/reporte")
    public ResponseEntity<List<CarreraConInscriptosDTO>> getAllCarrerasReporte() {
        try {
            List<CarreraConInscriptosDTO> reporteCarrera = carreraService.getAllCarrerasReporte();
            return ResponseEntity.ok(reporteCarrera);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
