package org.example.microservicioadministrador.controller;



import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.microservicioadministrador.dto.request.TarifaRequestDTO;
import org.example.microservicioadministrador.dto.response.TarifaResponseDTO;
import org.example.microservicioadministrador.entity.Tarifa;
import org.example.microservicioadministrador.entity.tipoTarifa;
import org.example.microservicioadministrador.service.TarifaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tarifa")
@RequiredArgsConstructor
public class TarifaController {

    private final TarifaService tarifaService;

    @PostMapping("/nueva")
    public ResponseEntity<?> createTarifa(@RequestBody TarifaRequestDTO tarifa) {
        try {
            TarifaResponseDTO tt = tarifaService.save(tarifa);
            return ResponseEntity.status(HttpStatus.CREATED).body(tt);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTarifa(@PathVariable Long id, @RequestBody TarifaRequestDTO tarifa) {
        try {
            TarifaResponseDTO tarifaActualizada = tarifaService.update(id, tarifa);
            return ResponseEntity.status(HttpStatus.OK).body(tarifaActualizada);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTarifa(@PathVariable Long id) {
        try {
            tarifaService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
    @DeleteMapping("/borrar")
    public ResponseEntity<?> deleteAllTarifas() {
        try {
            tarifaService.deleteTotal();
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\"}");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTarifaById(@PathVariable Long id) {
        try {
            TarifaResponseDTO tarifa = tarifaService.findById(id);
            return ResponseEntity.status(HttpStatus.OK).body(tarifa);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error. Por favor intente más tarde.\"}");
        }
    }
    @GetMapping("/total")
    public ResponseEntity<?> getAlltarifas() throws Exception {
        List<TarifaResponseDTO> tt =  tarifaService.findAll();
        return ResponseEntity.ok(tt);
    }
    @GetMapping ("/tipo/{tipo}")//la tarifa mas actual del tipo seleccionado
    public ResponseEntity<?> getTipoTarifa(@PathVariable tipoTarifa tipo) throws Exception {
        try{
            TarifaResponseDTO tarifa = tarifaService.findByTipo(tipo);
            return ResponseEntity.status(HttpStatus.OK).body(tarifa);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error\"}");
        }
    }


    @GetMapping("/aplicable/{tiempo}")
    public ResponseEntity<?> obtenerTarifaAplicable(@PathVariable Integer tiempo) {
        try {
            TarifaResponseDTO tarifaAplicable = tarifaService.obtenerTarifaAplicable(tiempo);
            return ResponseEntity.status(HttpStatus.OK)
                    .body(tarifaAplicable);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Error al obtener tarifa aplicable. " + e.getMessage() + "\"}");
        }
    }

}
