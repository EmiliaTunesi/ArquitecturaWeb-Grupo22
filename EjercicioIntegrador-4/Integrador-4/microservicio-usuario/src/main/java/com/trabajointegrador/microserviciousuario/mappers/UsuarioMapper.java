package com.trabajointegrador.microserviciousuario.mappers;

import com.trabajointegrador.microserviciousuario.dto.UsuarioDTO;
import com.trabajointegrador.microserviciousuario.entity.Usuario;

import java.util.Date;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        return new UsuarioDTO(
                usuario.getNombre_usuario(),
                usuario.getNombre(),
                usuario.getEmail(),
                usuario.getTelefono(),
                usuario.getFecha_registro() != null ? usuario.getFecha_registro() : new Date()
        );
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        return new Usuario(
                dto.getNombre_usuario(),
                dto.getNombre(),
                dto.getEmail(),
                dto.getTelefono(),
                dto.getFecha_registro() != null ? dto.getFecha_registro() : new Date()
        );
    }
}
