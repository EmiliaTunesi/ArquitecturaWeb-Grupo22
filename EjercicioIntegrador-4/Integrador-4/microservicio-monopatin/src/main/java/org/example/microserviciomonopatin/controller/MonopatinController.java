package org.example.microserviciomonopatin.controller;


import lombok.RequiredArgsConstructor;
import org.example.microserviciomonopatin.dto.dtoRequest.MonopatinRequestDTO;
import org.example.microserviciomonopatin.dto.dtoResponse.MonopatinResponseDTO;
import org.example.microserviciomonopatin.service.MonopatinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("/api/monopatines")
@RequiredArgsConstructor
public class MonopatinController {

    private final MonopatinService monopatinService;



    @PostMapping
    public ResponseEntity<MonopatinResponseDTO> agregarMonopatin(@RequestBody MonopatinRequestDTO request) {
        MonopatinResponseDTO response = monopatinService.agregarMonopatin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Quitar/eliminar monopatín
     * DELETE /api/monopatines/{id}
     */
    @PutMapping("/{id}/fuera-servicio")
    public ResponseEntity<MonopatinResponseDTO> marcarFueraDeServicio(@PathVariable Long id) {
        MonopatinResponseDTO monopatinActualizado = monopatinService.marcarFueraDeServicio(id);
        return ResponseEntity.ok(monopatinActualizado);
    }




    /**
     * Traer un monopatin
     * GET /api/monopatines
     */
    @GetMapping("/{id}")
    public ResponseEntity<MonopatinResponseDTO> obtenerMonopatin(@PathVariable Long id) {
        MonopatinResponseDTO response = monopatinService.obtenerMonopatinPorId(id);
        return ResponseEntity.ok(response);
    }

    /**
     * Listar todos los monopatines
     * GET /api/monopatines
     */
    @GetMapping
    public ResponseEntity<List<MonopatinResponseDTO>> listarMonopatines() {
        List<MonopatinResponseDTO> response = monopatinService.listarTodosLosMonopatines();
        return ResponseEntity.ok(response);
    }

}
