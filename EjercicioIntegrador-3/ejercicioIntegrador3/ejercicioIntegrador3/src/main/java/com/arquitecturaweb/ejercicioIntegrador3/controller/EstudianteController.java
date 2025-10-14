package com.arquitecturaweb.ejercicioIntegrador3.controller;


import com.arquitecturaweb.ejercicioIntegrador3.dto.request.EstudianteRequestDTO;
import com.arquitecturaweb.ejercicioIntegrador3.dto.response.EstudianteResponseDTO;
import com.arquitecturaweb.ejercicioIntegrador3.service.EstudianteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/estudiantes")
@AllArgsConstructor
public class EstudianteController {

    private EstudianteService estudianteService;

    @PostMapping
    public ResponseEntity<?> createEstudiante(@RequestBody EstudianteRequestDTO estudianteDTO) {
        try {
            EstudianteResponseDTO estudiante = estudianteService.save(estudianteDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(estudiante);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error al crear el estudiante: " + e.getMessage());
        }
    }
}
