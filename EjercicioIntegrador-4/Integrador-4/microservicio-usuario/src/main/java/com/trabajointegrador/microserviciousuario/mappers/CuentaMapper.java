package com.trabajointegrador.microserviciousuario.mappers;

import com.trabajointegrador.microserviciousuario.dto.CuentaDTO;
import com.trabajointegrador.microserviciousuario.entity.Cuenta;

public class CuentaMapper {

    public static CuentaDTO toDTO(Cuenta cuenta) {
        return new CuentaDTO(
                cuenta.getNumeroIdentificador(),
                cuenta.getFechaAlta(),
                cuenta.getTipoCuenta().name()
        );
    }

    public static Cuenta toEntity(CuentaDTO dto) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroIdentificador(dto.getNumeroIdentificador());
        cuenta.setFechaAlta(dto.getFechaAlta());
        return cuenta;
    }
}
