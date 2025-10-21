package com.arquitecturaweb.ejercicioIntegrador3.controller;


import com.arquitecturaweb.ejercicioIntegrador3.dto.request.EstudianteRequestDTO;
import com.arquitecturaweb.ejercicioIntegrador3.dto.response.EstudianteResponseDTO;
import com.arquitecturaweb.ejercicioIntegrador3.service.EstudianteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

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

    @GetMapping
    public ResponseEntity<List<EstudianteResponseDTO>> getAllEstudiantes() {
        List<EstudianteResponseDTO> estudiantes = estudianteService.getAllEstudiantesPorApellido();
        return ResponseEntity.ok(estudiantes);
    }

    @GetMapping("/lu/{lu}")
    public ResponseEntity<EstudianteResponseDTO> getEstudianteByLU(@PathVariable int lu) {
        try {
            EstudianteResponseDTO estudiante = estudianteService.getEstudianteByLU(lu);
            return ResponseEntity.ok(estudiante);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    //e) recuperar todos los estudiantes, en base a su género.
    @GetMapping("/genero/{genero}")
    public ResponseEntity<List<EstudianteResponseDTO>> getAllEstudiantesPorGenero(@PathVariable String genero) {
        try {
            List<EstudianteResponseDTO> estudiantesGenero = estudianteService.getAllEstudiantesPorGenero(genero);
            return ResponseEntity.ok(estudiantesGenero);
        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }


    //g) recuperar los estudiantes de una determinada carrera, filtrado por ciudad de residencia.
    @GetMapping("/carrera/{idCarrera}/ciudad/{ciudad}")
    public ResponseEntity<List<EstudianteResponseDTO>> getEstudiantesPorCarreraYCiudad(@PathVariable int idCarrera, @PathVariable String ciudad){
        try {
            List<EstudianteResponseDTO> lista = estudianteService.findByCarreraCiudad(idCarrera, ciudad);
            return ResponseEntity.ok(lista);
        } catch (Exception ex) {
            return ResponseEntity.notFound().build();
        }
        }
    }







