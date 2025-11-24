package com.trabajointegrador.microserviciousuario.controller;

import com.trabajointegrador.microserviciousuario.dto.UsuarioCuentaDTO;
import com.trabajointegrador.microserviciousuario.dto.UsuarioDTO;
import com.trabajointegrador.microserviciousuario.service.CuentaUsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/usuarios-cuentas")
public class UsuarioCuentaController {

    private final CuentaUsuarioService usuarioCuentaService;

    public UsuarioCuentaController(CuentaUsuarioService usuarioCuentaService) {
        this.usuarioCuentaService = usuarioCuentaService;
    }

    // ============ RUTAS SOLO ADMIN ============

    @PostMapping("/admin/vincular")
    public ResponseEntity<UsuarioCuentaDTO> vincularUsuarioCuenta(
            @RequestParam Long usuarioId,
            @RequestParam Long cuentaId
    ) {
        UsuarioCuentaDTO dto = usuarioCuentaService.vincularUsuarioCuenta(usuarioId, cuentaId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<UsuarioCuentaDTO>> listarVinculaciones() {
        List<UsuarioCuentaDTO> vinculaciones = usuarioCuentaService.listarVinculaciones();
        return new ResponseEntity<>(vinculaciones, HttpStatus.OK);
    }

    // ============ RUTAS PARA USER Y ADMIN (cualquier autenticado) ============

    @GetMapping("/existe")
    public ResponseEntity<Boolean> verificarVinculacion(
            @RequestParam Long usuarioId,
            @RequestParam Long cuentaId
    ) {
        // Usuario puede verificar su propia vinculación
        boolean existe = usuarioCuentaService.existeVinculacion(usuarioId, cuentaId);
        return new ResponseEntity<>(existe, HttpStatus.OK);
    }

    @GetMapping("/cuenta/{cuentaId}/usuarios")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuariosPorCuenta(
            @PathVariable Long cuentaId
    ) {
        // Usuario puede ver quiénes están en su misma cuenta
        List<UsuarioDTO> usuarios = usuarioCuentaService.obtenerUsuariosPorCuenta(cuentaId);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}