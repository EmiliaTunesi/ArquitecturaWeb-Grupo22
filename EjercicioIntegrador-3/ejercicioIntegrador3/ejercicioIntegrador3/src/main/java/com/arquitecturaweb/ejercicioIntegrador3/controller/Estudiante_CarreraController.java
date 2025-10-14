package com.arquitecturaweb.ejercicioIntegrador3.controller;


import com.arquitecturaweb.ejercicioIntegrador3.dto.request.Estudiante_CarreraRequestDTO;
import com.arquitecturaweb.ejercicioIntegrador3.dto.response.Estudiante_CarreraResponseDTO;
import com.arquitecturaweb.ejercicioIntegrador3.service.Estudiante_CarreraService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/estudiantes-carreras")
public class Estudiante_CarreraController {

    private Estudiante_CarreraService estudiante_CarreraService;


    @PostMapping
    public ResponseEntity<Estudiante_CarreraResponseDTO> matricularEstudiante(
            @Valid @RequestBody Estudiante_CarreraRequestDTO dto) {
        try {
            Estudiante_CarreraResponseDTO inscripcion = estudiante_CarreraService.save(dto);
            return new ResponseEntity<>(inscripcion, HttpStatus.CREATED);
        } catch (Exception e) {
            throw new RuntimeException("Error al inscribir estudiante", e);
        }
    }

}
