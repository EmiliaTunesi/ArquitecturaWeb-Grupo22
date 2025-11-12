package org.example.microservicioviaje.feignclients;

import org.example.microservicioviaje.model.Cuenta;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="microservicio-cuenta", url="http://localhost:8081/api/cuenta")
public interface CuentaFeignClient {

    @GetMapping("/{id}/disponible")
    Boolean isCuentaActiva(@PathVariable("id") Long id);

}