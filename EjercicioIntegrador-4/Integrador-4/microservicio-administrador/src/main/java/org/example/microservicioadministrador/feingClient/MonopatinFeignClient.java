package org.example.microservicioadministrador.feingClient;


import org.example.microservicioadministrador.dto.request.MantenimientoRequestDTO;
import org.example.microservicioadministrador.dto.request.MonopatinRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@FeignClient(name = "microservicio-monopatin")
@FeignClient(name = "microservicio-monopatin")
public interface MonopatinFeignClient {
    @GetMapping("/monopatines")
    ResponseEntity<List<MonopatinRequestDTO>> getAllMonopatin();

    @PostMapping("/monopatines")
    ResponseEntity<MonopatinRequestDTO> saveMonopatin(@RequestBody MonopatinRequestDTO monopatin);
    //utilizamos ResponseEntity porque da la respuesta completa:respuesta HTTP (status, headers, body).

    @PutMapping("/monopatines/{id}")
    ResponseEntity<MonopatinRequestDTO> updateMonopatin(@PathVariable Long id,
                                                        @RequestBody MonopatinRequestDTO monopatin);
    @DeleteMapping("/monopatines/{id}")
    ResponseEntity<Void> deleteMonopatin(@PathVariable Long id);

    @PostMapping("/monopatines/{id}/mantenimiento")
    ResponseEntity<MonopatinRequestDTO> agregarMantenimiento(@PathVariable Long id,@RequestBody MantenimientoRequestDTO mantenimiento);

    @PutMapping("/monopatines/{id}/mantenimiento/finalizar")
    ResponseEntity<MonopatinRequestDTO> finalizarMantenimiento(@PathVariable Long id,
                                                               @RequestBody MantenimientoRequestDTO mantenimiento);
}