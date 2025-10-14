package com.arquitecturaweb.ejercicioIntegrador3.controller;


import com.arquitecturaweb.ejercicioIntegrador3.dto.request.CarreraRequestDTO;
import com.arquitecturaweb.ejercicioIntegrador3.dto.response.CarreraResponseDTO;
import com.arquitecturaweb.ejercicioIntegrador3.service.CarreraService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
