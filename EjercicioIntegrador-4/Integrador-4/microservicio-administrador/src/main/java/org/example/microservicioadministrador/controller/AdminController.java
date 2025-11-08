package org.example.microservicioadministrador.controller;

import lombok.RequiredArgsConstructor;
import org.example.microservicioadministrador.dto.request.MantenimientoRequestDTO;
import org.example.microservicioadministrador.dto.request.MonopatinRequestDTO;
import org.example.microservicioadministrador.dto.request.ParadaRequestDTO;
import org.example.microservicioadministrador.service.AdministradorServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Supplier;

@RestController
@RequestMapping("/administrar")
public class AdminController {

    @Autowired
    private AdministradorServicio administradorServicio;

    @Autowired
    public AdminController(AdministradorServicio administradorServicio) {
        this.administradorServicio = administradorServicio;
    }

    @GetMapping("/monopatines")
    public ResponseEntity<?> obtenerTodosLosMonopatines() {
        return handleRequest(() -> administradorServicio.getAllMonopatines());
    }
    @PostMapping("/monopatines")
    public ResponseEntity<?> agregarMonopatin(@RequestBody MonopatinRequestDTO entity) {
        try {
            ResponseEntity<?> response = administradorServicio.addMonopatin(entity);
            System.out.println("Respuesta del servicio: " + response);
            return response;
        } catch (Exception e) {
            e.printStackTrace(); // Para ver el error completo en los logs
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }
    // Editar monopatín
    @PutMapping("/monopatines/{id}")
    public ResponseEntity<?> editarMonopatin(@PathVariable Long id,
                                             @RequestBody MonopatinRequestDTO entity) {
        try {
            ResponseEntity<?> response = administradorServicio.updateMonopatin(id, entity);
            System.out.println("Respuesta del servicio de actualización: " + response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    // Eliminar monopatín
    @DeleteMapping("/monopatines/{id}")
    public ResponseEntity<?> eliminarMonopatin(@PathVariable Long id) {
        try {
            ResponseEntity<?> response = administradorServicio.deleteMonopatin(id);
            System.out.println("Respuesta del servicio de eliminación: " + response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    //Paradas------------------------------------------------------------
    @PostMapping("/paradas")
    public ResponseEntity<?> agregarParada(@RequestBody ParadaRequestDTO entity) {
        return handleRequest(() -> administradorServicio.addParada(entity));
    }
    @GetMapping("/paradas")
    public ResponseEntity<?> obtenerTodasLasParadas() {
        return handleRequest(() -> administradorServicio.getAllParadas());
    }
    @PutMapping("/paradas/{id}")
    public ResponseEntity<?> editarParada(@PathVariable Long id, @RequestBody ParadaRequestDTO entity) {
        return handleRequest(() -> administradorServicio.updateParada(id, entity));
    }

    @DeleteMapping("/paradas/{id}")
    public ResponseEntity<?> eliminarParada(@PathVariable Long id) {
        return handleRequest(() -> administradorServicio.deleteParada(id));
    }

    // Método helper para manejar errores de manera consistente
    private ResponseEntity<?> handleRequest(Supplier<ResponseEntity<?>> action) {
        try {
            ResponseEntity<?> response = action.get();
            System.out.println("Respuesta del servicio: " + response);
            return response;
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"" + e.getMessage() + "\"}");
        }
    }

    //CUENTA-----------------------------------------------------------
    @PutMapping("/cuenta/anular/{id}")
    public ResponseEntity<?> anularCuenta(@PathVariable Long id){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(administradorServicio.anularCuenta(id));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\":\"Error. No se pudo anular la cuenta, revise los campos e intente nuevamente.\"}");
        }
    }
    //MANTENIMIENTO----------------------------------------------------------
    @PostMapping("/monopatines/{id}/mantenimiento")
    public ResponseEntity<?> agregarMantenimiento(
            @PathVariable Long id,
            @RequestBody MantenimientoRequestDTO mantenimiento) {

        try {
            return administradorServicio.agregarMantenimiento(id, mantenimiento);
            }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    @PutMapping("/monopatines/mantenimiento/finalizar/{id}")
    public ResponseEntity<?> finalizarMantenimiento(
            @PathVariable Long id,
            @RequestBody MantenimientoRequestDTO mantenimiento) {
        try {
            return administradorServicio.finalizarMantenimiento(id, mantenimiento);
        } catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
    //REPORTES-----------------------------------------------------------
    @GetMapping("/monopatines/kilometros/{id}")
    public ResponseEntity<?> generarReporteKilometros(
            @PathVariable Long id,
            @RequestParam(defaultValue = "false") Boolean incluirPausas) {
            //si no se envia por la URL por defecto es false
        return administradorServicio.generarReporteKilometros(id, incluirPausas);
    }
}
