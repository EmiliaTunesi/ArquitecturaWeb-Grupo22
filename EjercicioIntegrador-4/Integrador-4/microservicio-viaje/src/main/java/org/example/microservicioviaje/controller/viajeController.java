package org.example.microservicioviaje.controller;

import org.example.microservicioviaje.entity.viaje;
import org.example.microservicioviaje.DTO.monopatinviajeDTO;
import org.example.microservicioviaje.service.viajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.format.DateTimeParseException;
import org.example.microservicioviaje.DTO.ConteoViajesDTO;
import java.util.List;
import java.math.BigDecimal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;



@RestController
@RequestMapping("/viajes")
public class viajeController {

    @Autowired
    private viajeService viajeService;

    @GetMapping("")
    public ResponseEntity<List<viaje>> getAllViajes() {
        try {
            List<viaje> viajes = viajeService.findAll();
            return new ResponseEntity<>(viajes, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<viaje> getViajeById(@PathVariable Long id) {
        try {
            viaje viaje = viajeService.findById(id);
            if (viaje != null) {
                return new ResponseEntity<>(viaje, HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createViaje(@RequestBody viaje viaje) {
        try {
            viaje nuevoViaje = viajeService.save(viaje);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoViaje);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error. No se pudo ingresar, revise los campos e intente nuevamente.\"}");
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateViaje(@PathVariable Long id, @RequestBody viaje viaje) {
        try {
            viaje viajeActualizado = viajeService.update(id, viaje);
            if (viajeActualizado != null) {
                return new ResponseEntity<>(viajeActualizado, HttpStatus.OK);
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Viaje no encontrado.\"}");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error al actualizar el viaje.\"}");
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteViaje(@PathVariable Long id) {
        try {
            boolean eliminado = viajeService.delete(id);
            if (eliminado) {
                return ResponseEntity.status(HttpStatus.OK)
                        .body("{\"message\":\"Viaje eliminado correctamente.\"}");
            }
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("{\"error\":\"Viaje no encontrado.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error. No se pudo eliminar, intente nuevamente.\"}");
        }
    }
    //monopatin con mas de x viajes
    @GetMapping("/reporte/monopatines")
    public ResponseEntity<List<monopatinviajeDTO>> obtenerMonopatinesConMasDeXViajes(
            @RequestParam int anio,
            @RequestParam long minViajes) {

        try {
            List<monopatinviajeDTO> lista = viajeService.obtenerMonopatinesConMasDeXViajes(anio, minViajes);

            return new ResponseEntity<>(lista, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Devuelve todos los viajes de un año específico.
    @GetMapping("/por-anio/{anio}")
    public ResponseEntity<List<viaje>> getViajesByAnio(@PathVariable int anio) {
        try {
            List<viaje> viajes = viajeService.findAllByAnio(anio);

            return new ResponseEntity<>(viajes, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/conteo/usuario-cuenta")
    public ResponseEntity<?> getConteoUsuarioCuenta(
            @RequestParam Long idUsuario,
            @RequestParam Long idCuenta,
            @RequestParam String fechaDesde,
            @RequestParam String fechaHasta
    ) {
        try {
            ConteoViajesDTO conteo = viajeService.getConteoViajes(idUsuario, idCuenta, fechaDesde, fechaHasta);

            // Devolverá el JSON: {"cantidadViajesUsuario": X, "cantidadViajesCuenta": Y}
            return new ResponseEntity<>(conteo, HttpStatus.OK);

        } catch (DateTimeParseException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Formato de fecha inválido. Se esperaba YYYY-MM-DD.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error interno al procesar la solicitud: " + e.getMessage() + "\"}");
        }
    }
    //REPORTE (FACTURACIÓN)
    @GetMapping("/facturacion")
    public ResponseEntity<?> getReporteFacturacion(
            @RequestParam int anio,
            @RequestParam int mesInicio,
            @RequestParam int mesFin) {

        if (mesInicio > mesFin) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"El mes de inicio no puede ser mayor al mes de fin.\"}");
        }

        try {
            BigDecimal totalFacturado = viajeService.getTotalFacturado(anio, mesInicio, mesFin);

            // Devolvemos un JSON simple con el total
            return ResponseEntity.ok("{\"anio\": " + anio +
                    ", \"mesInicio\": " + mesInicio +
                    ", \"mesFin\": " + mesFin +
                    ", \"totalFacturado\": " + totalFacturado.toString() + "}");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("{\"error\":\"Error al procesar el reporte: " + e.getMessage() + "\"}");
        }
    }
}


