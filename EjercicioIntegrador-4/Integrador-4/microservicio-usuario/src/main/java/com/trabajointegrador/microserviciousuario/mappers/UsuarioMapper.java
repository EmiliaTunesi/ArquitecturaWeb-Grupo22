package com.trabajointegrador.microserviciousuario.mappers;

import com.trabajointegrador.microserviciousuario.dto.UsuarioDTO;
import com.trabajointegrador.microserviciousuario.entity.Usuario;

public class UsuarioMapper {

    public static UsuarioDTO toDTO(Usuario usuario) {
        System.out.println(">>> toDTO() ENTRÓ");
        System.out.println("USUARIO RECIBIDO: " + usuario);

        if (usuario == null) {
            System.out.println(">>> USUARIO ES NULL");
            return null;
        }

        UsuarioDTO dto = new UsuarioDTO();
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setNombre(usuario.getNombre());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setFechaRegistro(usuario.getFechaRegistro());

        System.out.println("DTO GENERADO: " + dto);
        return dto;
    }

    public static Usuario toEntity(UsuarioDTO dto) {
        System.out.println(">>> toEntity() ENTRÓ");
        System.out.println("DTO RECIBIDO: " + dto);

        if (dto == null) {
            System.out.println(">>> DTO ES NULL");
            return null;
        }

        Usuario usuario = new Usuario();
        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setFechaRegistro(dto.getFechaRegistro());

        System.out.println("ENTITY GENERADO: " + usuario);
        return usuario;
    }

    public static void updateEntity(Usuario usuario, UsuarioDTO dto) {
        System.out.println(">>> updateEntity() ENTRÓ");
        System.out.println("ENTITY ANTES: " + usuario);
        System.out.println("DTO: " + dto);

        if (usuario == null || dto == null) {
            System.out.println(">>> ALGUNO ES NULL");
            return;
        }

        usuario.setNombreUsuario(dto.getNombreUsuario());
        usuario.setNombre(dto.getNombre());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());

        if (dto.getFechaRegistro() != null) {
            usuario.setFechaRegistro(dto.getFechaRegistro());
        }

        System.out.println("ENTITY DESPUÉS: " + usuario);
    }
}
