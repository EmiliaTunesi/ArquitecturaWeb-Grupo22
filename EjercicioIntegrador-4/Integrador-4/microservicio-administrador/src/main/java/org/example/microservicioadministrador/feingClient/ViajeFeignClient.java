package org.example.microservicioadministrador.feingClient;

import org.example.microservicioadministrador.dto.response.MonopatinReportKmDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name="microservicio-viaje")

public interface ViajeFeignClient {
    @GetMapping("/viajes/monopatin/{id}/kilometros")
    ResponseEntity<MonopatinReportKmDTO> getKilometrosMonopatin(
            @PathVariable Long id,
            @RequestParam(required = false, defaultValue = "false") Boolean incluirPausas
    );
}
