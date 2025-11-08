package org.example.microservicioadministrador.feingClient;

import org.example.microservicioadministrador.dto.request.ParadaRequestDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="microservicio-parada") // Cambiar solo el nombre aquí
public interface ParadaFeignClient {

    @GetMapping("/paradas")
    ResponseEntity<List<ParadaRequestDTO>> getAllParadas();

    @PostMapping("/paradas/editar/{id}")
    ResponseEntity<ParadaRequestDTO> updateParada(@PathVariable Long id, ParadaRequestDTO parada);

    @PostMapping("/paradas")
    ResponseEntity<ParadaRequestDTO> createParada(@RequestBody ParadaRequestDTO parada);

    @DeleteMapping("/paradas/{id}")
    ResponseEntity<Void> deleteParada(@PathVariable Long id);
}
