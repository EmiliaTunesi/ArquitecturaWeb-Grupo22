package com.trabajointegrador.microserviciousuario.controller;

import com.trabajointegrador.microserviciousuario.dto.CuentaDTO;
import com.trabajointegrador.microserviciousuario.service.CuentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cuentas")
public class CuentaController {

    private final CuentaService cuentaService;

    public CuentaController(CuentaService cuentaService) {
        this.cuentaService = cuentaService;
    }

    // ============ RUTAS SOLO ADMIN ============

    @GetMapping
    public ResponseEntity<List<CuentaDTO>> listarCuentas() {
        List<CuentaDTO> cuentas = cuentaService.listarCuentas();
        return new ResponseEntity<>(cuentas, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CuentaDTO> actualizarCuenta(@PathVariable Long id, @RequestBody CuentaDTO dto) {
        CuentaDTO actualizada = cuentaService.actualizarCuenta(id, dto);
        return new ResponseEntity<>(actualizada, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCuenta(@PathVariable Long id) {
        cuentaService.eliminarCuenta(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/{id}/anular")
    public ResponseEntity<CuentaDTO> anularCuenta(@PathVariable Long id) {
        CuentaDTO cuentaAnulada = cuentaService.anularCuenta(id);
        return new ResponseEntity<>(cuentaAnulada, HttpStatus.OK);
    }

    @PatchMapping("/{id}/reactivar")
    public ResponseEntity<CuentaDTO> reactivarCuenta(@PathVariable Long id) {
        CuentaDTO cuentaReactivada = cuentaService.reactivarCuenta(id);
        return new ResponseEntity<>(cuentaReactivada, HttpStatus.OK);
    }

    @GetMapping("/activas")
    public ResponseEntity<List<CuentaDTO>> listarCuentasActivas() {
        List<CuentaDTO> cuentasActivas = cuentaService.listarCuentasActivas();
        return new ResponseEntity<>(cuentasActivas, HttpStatus.OK);
    }

    @GetMapping("/inactivas")
    public ResponseEntity<List<CuentaDTO>> listarCuentasInactivas() {
        List<CuentaDTO> cuentasInactivas = cuentaService.listarCuentasInactivas();
        return new ResponseEntity<>(cuentasInactivas, HttpStatus.OK);
    }

    // ============ RUTAS PARA USER Y ADMIN (cualquier autenticado) ============


    @PostMapping
    public ResponseEntity<CuentaDTO> crearCuenta(@RequestBody CuentaDTO dto) {
        CuentaDTO creada = cuentaService.crearCuenta(dto);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CuentaDTO> obtenerCuenta(@PathVariable Long id) {
        // Usuario puede consultar su propia cuenta
        CuentaDTO cuenta = cuentaService.obtenerPorId(id);
        return new ResponseEntity<>(cuenta, HttpStatus.OK);
    }

    @GetMapping("/{id}/es-premium")
    public ResponseEntity<Boolean> esCuentaPremium(@PathVariable Long id) {
        // Usuario puede verificar si su cuenta es premium
        return ResponseEntity.ok(cuentaService.esCuentaPremium(id));
    }

    @GetMapping("/{id}/disponible")
    public ResponseEntity<Boolean> isCuentaActiva(@PathVariable Long id) {
        // Usuario puede verificar si su cuenta está disponible
        boolean estaActiva = cuentaService.isCuentaDisponible(id);
        return ResponseEntity.ok(estaActiva);
    }
}