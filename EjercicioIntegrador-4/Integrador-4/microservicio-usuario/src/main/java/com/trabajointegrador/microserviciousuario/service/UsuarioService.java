package com.trabajointegrador.microserviciousuario.service;

import com.trabajointegrador.microserviciousuario.dto.UsuarioDTO;
import com.trabajointegrador.microserviciousuario.dto.UsuarioSimpleDTO;
import com.trabajointegrador.microserviciousuario.entity.Usuario;
import com.trabajointegrador.microserviciousuario.mappers.UsuarioMapper;
import com.trabajointegrador.microserviciousuario.repository.UsuarioRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;


@Service
@Slf4j
public class UsuarioService {

    private final UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {
        this.repo = repo;
    }

    public UsuarioDTO crearUsuario(UsuarioDTO dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);

        if (usuario.getFechaRegistro() == null) {
            usuario.setFechaRegistro(LocalDateTime.now());
        }
        Usuario guardado = repo.save(usuario);
        return UsuarioMapper.toDTO(guardado);
    }

    public UsuarioDTO actualizarUsuario(Long id, UsuarioDTO dto) {
        Usuario usuario = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        UsuarioMapper.updateEntity(usuario, dto);

        Usuario guardado = repo.save(usuario);
        return UsuarioMapper.toDTO(guardado);
    }

    public List<UsuarioDTO> listarUsuarios() {
        return repo.findAll().stream()
                .map(UsuarioMapper::toDTO)
                .toList();
    }

    public UsuarioDTO obtenerPorId(Long id) {
        return repo.findById(id)
                .map(UsuarioMapper::toDTO)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public List<UsuarioSimpleDTO> obtenerPorActivo(boolean activo) {
        return repo.findByActivo(activo)
                .stream()
                .map(u -> new UsuarioSimpleDTO(u.getNombreUsuario()))
                .toList();
    }

    public void eliminarUsuario(Long id) {
        repo.deleteById(id);
    }
}