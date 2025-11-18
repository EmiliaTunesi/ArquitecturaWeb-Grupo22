package com.trabajointegrador.microserviciousuario.mappers;

import com.trabajointegrador.microserviciousuario.dto.UsuarioDTO;
import com.trabajointegrador.microserviciousuario.entity.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }

        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setFechaRegistro(usuario.getFechaRegistro());
        dto.setActivo(usuario.isActivo());
        dto.setRol(usuario.getRol());
        dto.setPassword(usuario.getPassword());

        System.out.println("DTO GENERADO: " + dto);
        return dto;
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        if (dto == null) {
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setFechaRegistro(dto.getFechaRegistro());
        usuario.setActivo(dto.getActivo());
        usuario.setRol(dto.getRol());
        usuario.setPassword(dto.getPassword());
        return usuario;
    }

    public static void updateEntity(Usuario usuario, UsuarioDTO dto) {

        if (usuario == null || dto == null) {
            return;
        }

        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());


        if (dto.getFechaRegistro() != null) {
            usuario.setFechaRegistro(dto.getFechaRegistro());
        }
        usuario.setActivo(dto.getActivo());

        usuario.setRol(dto.getRol());
        usuario.setPassword(dto.getPassword());
    }
}
