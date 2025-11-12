package org.example.microservicioviaje.feignclients;

import org.example.microservicioviaje.model.Parada;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="microservicio-parada", url="http://localhost:8084/parada")
public interface ParadaFeignClient {
    @GetMapping("/{id}")
    Parada getParada(@PathVariable("id") Long id);

    @PostMapping
    Parada save(@RequestBody Parada parada);
}
