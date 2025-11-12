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

    /**
     * Registrar monopatín en mantenimiento
     * POST /api/mantenimientos
     */
    @PostMapping
    public ResponseEntity<MantenimientoResponseDTO> registrarMantenimiento(
            @RequestBody IniciarMantenimientoRequestDTO request) {
        MantenimientoResponseDTO response = mantenimientoService.registrarMantenimiento(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Registrar fin de mantenimiento
     * PUT /api/mantenimientos/{id}/finalizar
     */
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<MantenimientoResponseDTO> finalizarMantenimiento(
            @PathVariable Long id,
            @RequestBody(required = false) FinalizarMantenimientoRequestDTO request) {

        if (request == null) {
            request = new FinalizarMantenimientoRequestDTO("");
        }

        MantenimientoResponseDTO response = mantenimientoService.finalizarMantenimiento(id, request);
        return ResponseEntity.ok(response);
    }

    /**
     * Obtener mantenimiento por ID
     * GET /api/mantenimientos/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<MantenimientoResponseDTO> obtenerMantenimiento(@PathVariable Long id) {
        MantenimientoResponseDTO response = mantenimientoService.obtenerMantenimientoPorId(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Listar todos los mantenimientos
     * GET /api/mantenimientos
     */
    @GetMapping
    public ResponseEntity<List<MantenimientoResponseDTO>> listarMantenimientos() {
        List<MantenimientoResponseDTO> response = mantenimientoService.listarTodosLosMantenimientos();
        return ResponseEntity.ok(response);
    }

    /**
     * Listar mantenimientos por monopatín
     * GET /api/mantenimientos/monopatin/{monopatinId}
     */
    @GetMapping("/monopatin/{monopatinId}")
    public ResponseEntity<List<MantenimientoResponseDTO>> listarMantenimientosPorMonopatin(
            @PathVariable Long monopatinId) {
        List<MantenimientoResponseDTO> response = mantenimientoService.listarMantenimientosPorMonopatin(monopatinId);
        return ResponseEntity.ok(response);
    }
}
