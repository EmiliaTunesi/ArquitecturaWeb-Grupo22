package unicen.arq_web.microservicioparada.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import unicen.arq_web.microservicioparada.entities.Parada;
import unicen.arq_web.microservicioparada.models.ParadaDto;
import unicen.arq_web.microservicioparada.services.ParadaService;

import java.util.ArrayList;


@RestController
public class ParadaController {

    private ParadaService ps;

    @GetMapping("/parada/{id}")
    public ResponseEntity<ParadaDto> getById(@PathVariable("id") Integer id){
        try {
            ParadaDto dto = ps.getById(id);
            return ResponseEntity.ok(dto);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/paradas")
    public ResponseEntity<ArrayList<ParadaDto>> getAll(){
        ArrayList<ParadaDto> paradasList = ps.getAll();
        return new ResponseEntity<>(paradasList, HttpStatus.OK);
    }

    @PutMapping("parada/{id}")
    public ResponseEntity<ParadaDto> update(@PathVariable("id") Integer id, @RequestBody Parada parada){
        try {
            ParadaDto dto = ps.update(id, parada);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("parada/{id}")
    public ResponseEntity<ParadaDto> delete(@PathVariable("id") Integer id){
        try {
            ps.delete(id);
            return ResponseEntity.ok(null);
        }catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("paradas/new")
    public ResponseEntity<ParadaDto> add(@RequestBody Parada parada){
        try {
            ParadaDto dto = ps.add(parada);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("parada/{idParad}/estacionar/{idMonop}")
    public ResponseEntity<ParadaDto> estacionar(@PathVariable("idParad") Integer idParad, @PathVariable("idMonop") Long idMonop){
        try{
            ps.estacionar(idParad, idMonop);
            return ResponseEntity.ok(null);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }





}
