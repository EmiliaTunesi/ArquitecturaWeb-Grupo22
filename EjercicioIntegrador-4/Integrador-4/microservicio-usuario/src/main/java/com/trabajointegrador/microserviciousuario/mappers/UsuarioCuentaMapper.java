package com.trabajointegrador.microserviciousuario.mappers;

import com.trabajointegrador.microserviciousuario.dto.UsuarioCuentaDTO;
import com.trabajointegrador.microserviciousuario.entity.UsuarioCuenta;

public class UsuarioCuentaMapper {

    public static UsuarioCuentaDTO toDTO(UsuarioCuenta entity) {
        UsuarioCuentaDTO dto = new UsuarioCuentaDTO();
        dto.setNombreUsuario(entity.getUsuario().getNombreUsuario());
        dto.setNumeroCuenta(entity.getCuenta().getNumeroCuenta());
        dto.setFechaVinculacion(entity.getFechaVinculacion());
        return dto;
    }
}