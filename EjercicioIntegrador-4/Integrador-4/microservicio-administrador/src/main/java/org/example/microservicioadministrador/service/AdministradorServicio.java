package org.example.microservicioadministrador.service;

import lombok.RequiredArgsConstructor;
import org.example.microservicioadministrador.dto.request.MantenimientoRequestDTO;
import org.example.microservicioadministrador.dto.request.MonopatinRequestDTO;
import org.example.microservicioadministrador.dto.request.ParadaRequestDTO;
import org.example.microservicioadministrador.dto.response.MonopatinReportKmDTO;
import org.example.microservicioadministrador.feingClient.MonopatinFeignClient;
import org.example.microservicioadministrador.feingClient.ParadaFeignClient;
import org.example.microservicioadministrador.feingClient.UsuarioFeignClient;
import org.example.microservicioadministrador.feingClient.ViajeFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
//@Transactional se usa cuando quiero que las op en la BD sea atomica
@RequiredArgsConstructor
public class AdministradorServicio {

    @Autowired
    MonopatinFeignClient monopatinFeignClient;
    ParadaFeignClient paradaFeignClient;
    UsuarioFeignClient usuarioFeignClient;
    ViajeFeignClient viajeFeignClient;


    public ResponseEntity<List<MonopatinRequestDTO>> getAllMonopatines() {
        ResponseEntity<List<MonopatinRequestDTO>> response = monopatinFeignClient.getAllMonopatin();
        return ResponseEntity.ok().body(response.getBody());
    }

    public ResponseEntity addMonopatin (MonopatinRequestDTO monopatin){
        ResponseEntity<MonopatinRequestDTO> response = monopatinFeignClient.saveMonopatin(monopatin);
        return ResponseEntity.ok().body(response.getBody());
    }
/*
POST http://localhost:8081/administrar/monopatines
Content-Type: application/json

{
    "estado": "DISPONIBLE",
    "latitudActual": -34.9214,
    "longitudActual": -57.9544,
    "kilometros": 0.0,
    "tiempoUso": 0,
    "tiempoPausa": 0,
    "fechaAlta": "2025-11-07T13:34:49"
}
 */
    // Actualizar monopatín

    public ResponseEntity updateMonopatin(Long id, MonopatinRequestDTO monopatin) {
        ResponseEntity<MonopatinRequestDTO> response = monopatinFeignClient.updateMonopatin(id, monopatin);
        return ResponseEntity.ok().body(response.getBody());
    }

    // Eliminar monopatín

    public ResponseEntity deleteMonopatin(Long id) {
        ResponseEntity<Void> response = monopatinFeignClient.deleteMonopatin(id);
        return ResponseEntity.ok().build(); // Retornamos 200 OK sin cuerpo
    }

    //paradas---------------------------------------------------------
    public ResponseEntity addParada(ParadaRequestDTO parada) {
        ResponseEntity<ParadaRequestDTO> response = paradaFeignClient.createParada(parada);
        return ResponseEntity.ok().body(response.getBody());
    }
    public ResponseEntity<List<ParadaRequestDTO>> getAllParadas() {
        ResponseEntity<List<ParadaRequestDTO>> response = paradaFeignClient.getAllParadas();
        return ResponseEntity.ok().body(response.getBody());
    }
    public ResponseEntity updateParada(Long id, ParadaRequestDTO parada) {
        ResponseEntity<ParadaRequestDTO> response = paradaFeignClient.updateParada(id, parada);
        return ResponseEntity.ok().body(response.getBody());
    }

    public ResponseEntity deleteParada(Long id) {
        ResponseEntity<Void> response = paradaFeignClient.deleteParada(id);
        return ResponseEntity.ok().build();
    }

    //CUENTA-----------------------------------------------------
    public ResponseEntity<?> anularCuenta(Long id){
        try{
            return ResponseEntity.ok(usuarioFeignClient.anularCuenta(id));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage()+"fallo");
        }
    }
    //MANTENIMIENTO---------------------------------------------
    public ResponseEntity<?> agregarMantenimiento(Long monopatinId, MantenimientoRequestDTO mantenimiento) {
        try {
            // Aseguramos que el ID del monopatín en el DTO coincida con el de la URL
            ResponseEntity<MonopatinRequestDTO> response =
                    monopatinFeignClient.agregarMantenimiento(monopatinId, mantenimiento);

            return ResponseEntity.ok().body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    public ResponseEntity<?> finalizarMantenimiento(Long monopatinId, MantenimientoRequestDTO mantenimiento) {
        try {
            ResponseEntity<MonopatinRequestDTO> response =
                    monopatinFeignClient.finalizarMantenimiento(monopatinId, mantenimiento);
            return ResponseEntity.ok().body(response.getBody());
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    //REPORTES--------------------------------------------------------------------------------------------
    public ResponseEntity<?> generarReporteKilometros(Long monopatinId, Boolean incluirPausas) {
        try {
            ResponseEntity<MonopatinReportKmDTO> response =
                    viajeFeignClient.getKilometrosMonopatin(monopatinId, incluirPausas);

            if (response.getBody() == null) {
                return ResponseEntity.notFound().build();
            }
            MonopatinReportKmDTO reporte = response.getBody();
            reporte.setIncluyePausas(incluirPausas);

            return ResponseEntity.ok().body(reporte);
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body("{\"error\": \"Error al generar reporte: " + e.getMessage() + "\"}");
        }
    }
}
