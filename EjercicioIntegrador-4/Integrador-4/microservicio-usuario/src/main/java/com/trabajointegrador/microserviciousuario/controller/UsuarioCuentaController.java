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

    // Crear vínculo entre un usuario y una cuenta
    @PostMapping("/vincular")
    public ResponseEntity<UsuarioCuentaDTO> vincularUsuarioCuenta(
            @RequestParam Long usuarioId,
            @RequestParam Long cuentaId
    ) {
        UsuarioCuentaDTO dto = usuarioCuentaService.vincularUsuarioCuenta(usuarioId, cuentaId);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    // Listar todas las vinculaciones
    @GetMapping
    public ResponseEntity<List<UsuarioCuentaDTO>> listarVinculaciones() {
        List<UsuarioCuentaDTO> vinculaciones = usuarioCuentaService.listarVinculaciones();
        return new ResponseEntity<>(vinculaciones, HttpStatus.OK);
    }

    // Consultar si un usuario está vinculado a una cuenta
    @GetMapping("/existe")
    public ResponseEntity<Boolean> verificarVinculacion(
            @RequestParam Long usuarioId,
            @RequestParam Long cuentaId
    ) {
        boolean existe = usuarioCuentaService.existeVinculacion(usuarioId, cuentaId);
        return new ResponseEntity<>(existe, HttpStatus.OK);
    }

    @GetMapping("/cuenta/{cuentaId}/usuarios")
    public ResponseEntity<List<UsuarioDTO>> obtenerUsuariosPorCuenta(
            @PathVariable Long cuentaId
    ) {
        List<UsuarioDTO> usuarios = usuarioCuentaService.obtenerUsuariosPorCuenta(cuentaId);
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }
}
