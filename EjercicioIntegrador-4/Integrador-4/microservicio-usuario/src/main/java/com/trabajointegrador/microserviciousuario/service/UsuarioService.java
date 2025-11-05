package com.trabajointegrador.microserviciousuario.service;

import com.trabajointegrador.microserviciousuario.dto.UsuarioDTO;
import com.trabajointegrador.microserviciousuario.entity.Usuario;
import com.trabajointegrador.microserviciousuario.mappers.UsuarioMapper;
import com.trabajointegrador.microserviciousuario.repository.UsuarioRepository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional
    public UsuarioDTO crearUsuario(UsuarioDTO dto) {
        Usuario usuario = UsuarioMapper.toEntity(dto);
        Usuario guardado = usuarioRepository.save(usuario);
        return UsuarioMapper.toDTO(guardado);
    }

    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarUsuarios() {
        return usuarioRepository.findAll()
                .stream()
                .map(UsuarioMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public UsuarioDTO obtenerPorId(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return UsuarioMapper.toDTO(usuario);
    }

    @Transactional
    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}
