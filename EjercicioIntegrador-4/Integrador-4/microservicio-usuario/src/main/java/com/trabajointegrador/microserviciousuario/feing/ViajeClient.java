package com.trabajointegrador.microserviciousuario.feing;


import com.trabajointegrador.microserviciousuario.dto.ConteoViajesDTO;

import com.trabajointegrador.microserviciousuario.dto.ViajeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "viaje-service", url = "http://localhost:8083/viajes")
public interface ViajeClient {

    @GetMapping("/conteo/usuario-cuenta")
    ConteoViajesDTO obtenerConteos(
            @RequestParam("idUsuario") Long idUsuario,
            @RequestParam("idCuenta") Long idCuenta,
            @RequestParam("fechaDesde") String fechaDesde,
            @RequestParam("fechaHasta") String fechaHasta
    );

    @GetMapping("/por-anio/{anio}")
    List<ViajeDTO> obtenerViajesPorAnio(@PathVariable("anio") int anio);
}