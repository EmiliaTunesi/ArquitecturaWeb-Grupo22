package unicen.arq_web.microservicioparada.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
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

    @Autowired
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

    @PostMapping("paradas/new")
    public ResponseEntity<ParadaDto> add(@RequestBody Parada parada){
        try {
            ParadaDto dto = ps.add(parada);
            return ResponseEntity.ok(dto);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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


    @PutMapping("parada/{idParad}/estacionar/{idMonop}")
    public ResponseEntity<ParadaDto> estacionar(@PathVariable("idParad") Integer idParada, @PathVariable("idMonop") Long idMonopatin){
        try{
            ps.estacionar(idParada, idMonopatin);
            return ResponseEntity.ok(null);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PatchMapping("parada/{idParad}/estacionar/{idMonop}")
    public ResponseEntity<Boolean> quitarMonopatin(@PathVariable("idParad") Integer idParada, @PathVariable("idMonop") Long idMonopatin) {
        try {
            ps.quitarMonopatin(idParada, idMonopatin);
            return ResponseEntity.ok(true);
        }
        catch (RuntimeException e){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("paradas/cercanas")
    public ResponseEntity<?> getCercanas(@RequestParam Double lat, @RequestParam Double longit) {
        Pair<Double, Double> salida = ps.getCercanas(lat, longit);
        if (salida != null){
            return ResponseEntity.ok(salida);
        }else  {
            return ResponseEntity.ok("No hay paradas cercanas con monopatines disponibles ");
        }
    }





}
