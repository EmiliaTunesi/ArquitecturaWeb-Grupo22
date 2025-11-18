package org.example.microserviciomonopatin.controller;


import lombok.RequiredArgsConstructor;
import org.example.microserviciomonopatin.dto.dtoRequest.ActualizacionMonopatinDTO;
import org.example.microserviciomonopatin.dto.dtoRequest.ActualizarUbicacionDTO;
import org.example.microserviciomonopatin.dto.dtoRequest.MonopatinRequestDTO;
import org.example.microserviciomonopatin.dto.dtoResponse.MonopatinResponseDTO;
import org.example.microserviciomonopatin.dto.dtoResponse.ReporteUsoMonopatinDTO;
import org.example.microserviciomonopatin.service.MonopatinService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/monopatines")
@RequiredArgsConstructor
public class MonopatinController {

    private final MonopatinService monopatinService;



    //Agrego un monopatin
    @PostMapping
    public ResponseEntity<MonopatinResponseDTO> agregarMonopatin(@RequestBody MonopatinRequestDTO request) {
        MonopatinResponseDTO response = monopatinService.agregarMonopatin(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //Borrado logico de un monopatin donde monopatin = FUERA_DE_SERVICIO
    @PutMapping("/{id}/fuera-servicio")
    public ResponseEntity<MonopatinResponseDTO> marcarFueraDeServicio(@PathVariable String id) {
        MonopatinResponseDTO monopatinActualizado = monopatinService.marcarFueraDeServicio(id);
        return ResponseEntity.ok(monopatinActualizado);
    }



    //Devuelve true o false, si es true cambia el estado del monopatin=EN_USO
    @GetMapping("/{id}/disponible")
    public ResponseEntity<Boolean> verificarDisponibilidad(@PathVariable String id) {
        boolean disponible = monopatinService.estaDisponible(id);
        return ResponseEntity.ok(disponible);
    }





    //Finaliza el uso del monopatin
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<String> finalizarUso(
            @PathVariable String id,
            @RequestBody ActualizacionMonopatinDTO actualizacionDTO) {
        monopatinService.finalizarUso(id, actualizacionDTO);
        return ResponseEntity.ok("Monopatín actualizado y disponible nuevamente");
    }



    //Traer un monopatin
    @GetMapping("/{id}")
    public ResponseEntity<MonopatinResponseDTO> obtenerMonopatin(@PathVariable String id) {
        MonopatinResponseDTO response = monopatinService.obtenerMonopatinPorId(id);
        return ResponseEntity.ok(response);
    }

    //Listar todos los monopatines
    @GetMapping
    public ResponseEntity<List<MonopatinResponseDTO>> listarMonopatines() {
        List<MonopatinResponseDTO> response = monopatinService.listarTodosLosMonopatines();
        return ResponseEntity.ok(response);
    }


    //Listar monopatines para ver si necesitan mantenimiento, puede o no incluir pausas
    @GetMapping("/reporte-uso")
    public ResponseEntity<List<ReporteUsoMonopatinDTO>> generarReporte(
            @RequestParam(defaultValue = "false") boolean incluirPausas) {

        List<ReporteUsoMonopatinDTO> reporte = monopatinService.generarReporte(incluirPausas);
        return ResponseEntity.ok(reporte);
    }

}
