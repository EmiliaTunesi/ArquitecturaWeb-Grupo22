package org.example.microservicioviaje.feignclients;

import org.example.microservicioviaje.model.Monopatin;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.example.microservicioviaje.DTO.MonopatinUsoDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="microservicio-monopatin", url="http://localhost:8085/api/monopatines")
public interface MonopatinFeignClient {
    @GetMapping("/{id}")
    Monopatin getMonopatin(@PathVariable("id") Long id);

    @PostMapping
    Monopatin save(@RequestBody Monopatin monopatin);

    // Llama al microservicio de monopatin para verificar si está disponible.
    //GET http://localhost:8085/monopatin/{id}/esta-disponible

    @GetMapping("/{id}/disponible")
    Boolean estaDisponible(@PathVariable("id") Long id);

    //Envía los datos de uso de este viaje para que el microservicio de monopatin los sume a sus totales.

    @PutMapping("/{id}/actualizar-uso")
    ResponseEntity<Void> actualizarUso(
            @PathVariable("id") Long id,
            @RequestBody MonopatinUsoDTO usoDTO
    );

}
