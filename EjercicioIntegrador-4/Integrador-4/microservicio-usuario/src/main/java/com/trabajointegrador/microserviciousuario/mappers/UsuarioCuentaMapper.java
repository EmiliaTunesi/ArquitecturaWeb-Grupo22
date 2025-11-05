package com.trabajointegrador.microserviciousuario.mappers;

import com.trabajointegrador.microserviciousuario.dto.UsuarioCuentaDTO;
import com.trabajointegrador.microserviciousuario.entity.UsuarioCuenta;

public class UsuarioCuentaMapper {

    public static UsuarioCuentaDTO toDTO(UsuarioCuenta usuarioCuenta) {
        return new UsuarioCuentaDTO(
                usuarioCuenta.getUsuario().getNombre_usuario(),
                usuarioCuenta.getCuenta().getNumeroIdentificador(),
                usuarioCuenta.isActiva(),
                usuarioCuenta.getFechaVinculacion()
        );
    }
}
