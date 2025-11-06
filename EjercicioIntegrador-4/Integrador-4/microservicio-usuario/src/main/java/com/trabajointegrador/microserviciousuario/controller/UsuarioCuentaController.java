package com.trabajointegrador.microserviciousuario.controller;

import com.trabajointegrador.microserviciousuario.dto.UsuarioCuentaDTO;
import com.trabajointegrador.microserviciousuario.service.CuentaUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios-cuentas")
public class UsuarioCuentaController {

    private final CuentaUsuarioService usuarioCuentaService;

    public UsuarioCuentaController(CuentaUsuarioService usuarioCuentaService) {
        this.usuarioCuentaService = usuarioCuentaService;
    }

    // 🔹 Crear vínculo entre un usuario y una cuenta
    @PostMapping("/vincular")
    public ResponseEntity<UsuarioCuentaDTO> vincularUsuarioCuenta(
            @RequestParam Long usuarioId,
            @RequestParam Long cuentaId
    ) {
        UsuarioCuentaDTO dto = usuarioCuentaService.vincularUsuarioCuenta(usuarioId, cuentaId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // 🔹 Desactivar vínculo (el usuario deja de tener acceso a esa cuenta)
    @PatchMapping("/desactivar")
    public ResponseEntity<Void> desactivarVinculacion(
            @RequestParam Long usuarioId,
            @RequestParam Long cuentaId
    ) {
        usuarioCuentaService.desactivarVinculacion(usuarioId, cuentaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 🔹 Reactivar vínculo previamente desactivado
    @PatchMapping("/reactivar")
    public ResponseEntity<Void> reactivarVinculacion(
            @RequestParam Long usuarioId,
            @RequestParam Long cuentaId
    ) {
        usuarioCuentaService.reactivarVinculacion(usuarioId, cuentaId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // 🔹 Listar todas las vinculaciones
    @GetMapping
    public ResponseEntity<List<UsuarioCuentaDTO>> listarVinculaciones() {
        List<UsuarioCuentaDTO> vinculaciones = usuarioCuentaService.listarVinculaciones();
        return new ResponseEntity<>(vinculaciones, HttpStatus.OK);
    }

    // 🔹 Listar solo las activas
    @GetMapping("/activas")
    public ResponseEntity<List<UsuarioCuentaDTO>> listarVinculacionesActivas() {
        List<UsuarioCuentaDTO> activas = usuarioCuentaService.listarVinculacionesActivas();
        return new ResponseEntity<>(activas, HttpStatus.OK);
    }

    // 🔹 Listar solo las inactivas
    @GetMapping("/inactivas")
    public ResponseEntity<List<UsuarioCuentaDTO>> listarVinculacionesInactivas() {
        List<UsuarioCuentaDTO> inactivas = usuarioCuentaService.listarVinculacionesInactivas();
        return new ResponseEntity<>(inactivas, HttpStatus.OK);
    }

    // 🔹 Consultar si un usuario está vinculado a una cuenta
    @GetMapping("/existe")
    public ResponseEntity<Boolean> verificarVinculacion(
            @RequestParam Long usuarioId,
            @RequestParam Long cuentaId
    ) {
        boolean existe = usuarioCuentaService.existeVinculacion(usuarioId, cuentaId);
        return new ResponseEntity<>(existe, HttpStatus.OK);
    }
}