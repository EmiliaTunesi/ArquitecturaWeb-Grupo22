package com.trabajointegrador.microserviciousuario.controller;

import com.trabajointegrador.microserviciousuario.dto.UsuarioDTO;
import com.trabajointegrador.microserviciousuario.mappers.UsuarioMapper;
import com.trabajointegrador.microserviciousuario.service.UsuarioService;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // Crear un usuario
    @PostMapping
    public ResponseEntity<UsuarioDTO> crearUsuario(@RequestBody UsuarioDTO dto) {
        UsuarioDTO creado = usuarioService.crearUsuario(dto);
        return new ResponseEntity<>(creado, HttpStatus.CREATED);
    }

    // Listar todos los usuarios
    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listarUsuarios() {
        List<UsuarioDTO> usuarios = usuarioService.listarUsuarios();
        return new ResponseEntity<>(usuarios, HttpStatus.OK);
    }

    // Obtener usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> obtenerUsuario(@PathVariable Long id) {
        UsuarioDTO usuario = usuarioService.obtenerPorId(id);
        return new ResponseEntity<>(usuario, HttpStatus.OK);
    }

    // Actualizar usuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> actualizarUsuario(@PathVariable Long id, @RequestBody UsuarioDTO dto) {
        UsuarioDTO usuarioExistente = usuarioService.obtenerPorId(id);

        usuarioExistente.setNombre(dto.getNombre());
        usuarioExistente.setNombre_usuario(dto.getNombre_usuario());
        usuarioExistente.setEmail(dto.getEmail());
        usuarioExistente.setTelefono(dto.getTelefono());

        UsuarioDTO actualizado = usuarioService.crearUsuario(UsuarioMapper.toDTO(UsuarioMapper.toEntity(usuarioExistente)));
        return new ResponseEntity<>(actualizado, HttpStatus.OK);
    }

    // Eliminar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        usuarioService.obtenerPorId(id);
        usuarioService.eliminarUsuario(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
