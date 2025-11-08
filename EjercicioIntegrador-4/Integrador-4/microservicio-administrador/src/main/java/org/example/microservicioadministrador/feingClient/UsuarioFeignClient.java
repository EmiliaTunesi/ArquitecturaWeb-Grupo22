package org.example.microservicioadministrador.feingClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

@FeignClient(name="microservicio-usuario")
public interface UsuarioFeignClient {

    @PutMapping("/cuenta/anular/{id}")
    ResponseEntity<Void> anularCuenta(@PathVariable Long id);
}
