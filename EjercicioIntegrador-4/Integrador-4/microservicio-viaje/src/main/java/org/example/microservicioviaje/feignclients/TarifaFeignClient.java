package org.example.microservicioviaje.feignclients;

import org.example.microservicioviaje.model.Tarifa;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.example.microservicioviaje.DTO.TarifaCalculadaDTO;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name="microservicio-tarifa", url="http://localhost:8088/tarifa")
public interface TarifaFeignClient {

    @GetMapping("/{id}")
    Tarifa getTarifa(@PathVariable("id") Long id);

    @PostMapping
    Tarifa save(@RequestBody Tarifa tarifa);

    @GetMapping("/aplicable/tiempo")
    TarifaCalculadaDTO getTarifaAplicable(@RequestParam("tiempo") int tiempoPausaMinutos);
}
