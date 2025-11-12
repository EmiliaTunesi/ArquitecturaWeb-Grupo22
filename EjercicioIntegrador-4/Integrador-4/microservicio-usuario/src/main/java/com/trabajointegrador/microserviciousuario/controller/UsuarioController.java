package com.trabajointegrador.microserviciousuario.controller;

import com.trabajointegrador.microserviciousuario.dto.UsuarioDTO;
import com.trabajointegrador.microserviciousuario.dto.UsuarioRankingDTO;
import com.trabajointegrador.microserviciousuario.dto.UsuarioSimpleDTO;
import com.trabajointegrador.microserviciousuario.dto.UsuarioUsoDTO;
import com.trabajointegrador.microserviciousuario.service.UsuarioService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> crear(@RequestBody UsuarioDTO dto) {
        UsuarioDTO respuesta = usuarioService.crearUsuario(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(respuesta);
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(usuarioService.listarUsuarios());
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtener(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.obtenerPorId(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizar(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        return ResponseEntity.ok(usuarioService.actualizarUsuario(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> borrar(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filtrar")
    public ResponseEntity<List<UsuarioSimpleDTO>> filtrarActivos(
            @RequestParam boolean activo
    ) {
        return ResponseEntity.ok(usuarioService.obtenerPorActivo(activo));
    }

    @GetMapping("/uso")
    public ResponseEntity<UsuarioUsoDTO> obtenerUso(
            @RequestParam Long idUsuario,
            @RequestParam Long idCuenta,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        UsuarioUsoDTO result = usuarioService.obtenerUso(idUsuario, idCuenta, desde, hasta);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/top-usuarios")
    public ResponseEntity<List<UsuarioRankingDTO>> topUsuarios(
            @RequestParam int anio,
            @RequestParam(required = false) Boolean activo
    ) {
        return ResponseEntity.ok(usuarioService.obtenerTopUsuarios(anio, activo));
    }

}