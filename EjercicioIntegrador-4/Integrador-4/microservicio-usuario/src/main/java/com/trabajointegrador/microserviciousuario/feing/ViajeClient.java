package com.trabajointegrador.microserviciousuario.feing;


import com.trabajointegrador.microserviciousuario.dto.ViajeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "viaje-service", url = "http://localhost:8083")
public interface ViajeClient {

    @GetMapping("/api/viajes/por-cuenta")
    List<ViajeDTO> obtenerViajesPorCuenta(
            @RequestParam Long idCuenta,
            @RequestParam String fechaDesde,
            @RequestParam String fechaHasta
    );
}
