package org.example.microserviciomonopatin.controller;


import lombok.RequiredArgsConstructor;
import org.example.microserviciomonopatin.dto.dtoRequest.FinalizarMantenimientoRequestDTO;
import org.example.microserviciomonopatin.dto.dtoRequest.IniciarMantenimientoRequestDTO;
import org.example.microserviciomonopatin.dto.dtoResponse.MantenimientoResponseDTO;
import org.example.microserviciomonopatin.service.MantenimientoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mantenimientos")
@RequiredArgsConstructor
public class MantenimientoController {

    private final MantenimientoService mantenimientoService;

    //Crear un mantemiento para un monopatin
    @PostMapping
    public ResponseEntity<MantenimientoResponseDTO> registrarMantenimiento(
            @RequestBody IniciarMantenimientoRequestDTO request) {
        MantenimientoResponseDTO response = mantenimientoService.registrarMantenimiento(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Termina un mantenimiento
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<MantenimientoResponseDTO> finalizarMantenimiento(
            @PathVariable String id,
            @RequestBody(required = false) FinalizarMantenimientoRequestDTO request) {

        if (request == null) {
            request = new FinalizarMantenimientoRequestDTO("");
        }

        MantenimientoResponseDTO response = mantenimientoService.finalizarMantenimiento(id, request);
        return ResponseEntity.ok(response);
    }

    //Me devuelve un mantenimiento por ID
    @GetMapping("/{id}")
    public ResponseEntity<MantenimientoResponseDTO> obtenerMantenimiento(@PathVariable String id) {
        MantenimientoResponseDTO response = mantenimientoService.obtenerMantenimientoPorId(id);
        return ResponseEntity.ok(response);
    }

    //Listar todos los mantenimientos
    @GetMapping
    public ResponseEntity<List<MantenimientoResponseDTO>> listarMantenimientos() {
        List<MantenimientoResponseDTO> response = mantenimientoService.listarTodosLosMantenimientos();
        return ResponseEntity.ok(response);
    }

    //Me trae todos los mantenimientos por un monopatin
    @GetMapping("/monopatin/{monopatinId}")
    public ResponseEntity<List<MantenimientoResponseDTO>> listarMantenimientosPorMonopatin(
            @PathVariable String monopatinId) {
        List<MantenimientoResponseDTO> response = mantenimientoService.listarMantenimientosPorMonopatin(monopatinId);
        return ResponseEntity.ok(response);
    }
}
