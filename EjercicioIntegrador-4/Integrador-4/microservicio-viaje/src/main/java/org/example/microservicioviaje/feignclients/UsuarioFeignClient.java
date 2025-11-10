package org.example.microservicioviaje.feignclients;

import org.example.microservicioviaje.model.Usuario; // Necesitaremos crear este modelo
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

// PONER EL LOCAL HOST
@FeignClient(name="microservicio-usuario", url="http://localhost:8081/usuario")
public interface UsuarioFeignClient {

    // Este es un ejemplo, dime qué endpoints necesitas
    @GetMapping("/{id}")
    Usuario getUsuario(@PathVariable("id") Long id);

    @GetMapping("/{id}/es-premium")
    Boolean esUsuarioPremium(@PathVariable("id") Long id);

}