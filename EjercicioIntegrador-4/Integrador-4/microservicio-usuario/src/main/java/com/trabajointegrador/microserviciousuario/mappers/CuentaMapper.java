package com.trabajointegrador.microserviciousuario.mappers;

import com.trabajointegrador.microserviciousuario.dto.CuentaDTO;
import com.trabajointegrador.microserviciousuario.entity.Cuenta;

import java.time.LocalDate;

public class CuentaMapper {

    public static CuentaDTO toDTO(Cuenta cuenta) {
        CuentaDTO dto = new CuentaDTO();
        dto.setNumeroIdentificador(cuenta.getNumeroCuenta());
        dto.setFechaAlta(cuenta.getFechaAlta());
        dto.setTipoCuenta(cuenta.getTipoCuenta().name());
        dto.setActiva(cuenta.isActiva());
        dto.setFechaBaja(cuenta.getFechaBaja());
        dto.setCuentaMercadoPagoId(cuenta.getCuentaMercadoPagoId());
        dto.setKmRecorridosMes(cuenta.getKmRecorridosMes());
        dto.setSaldoCreditos(cuenta.getSaldoCreditos());
        dto.setFechaRenovacionCupo(cuenta.getFechaRenovacionCupo());

        return dto;
    }

    public static Cuenta toEntity(CuentaDTO dto) {
        Cuenta cuenta = new Cuenta();
        cuenta.setNumeroCuenta(dto.getNumeroIdentificador());
        cuenta.setFechaAlta(dto.getFechaAlta() != null ? dto.getFechaAlta() : LocalDate.now());
        cuenta.setTipoCuenta(Cuenta.TipoCuenta.valueOf(dto.getTipoCuenta().toUpperCase()));
        cuenta.setActiva(dto.isActiva());
        cuenta.setFechaBaja(dto.getFechaBaja());
        cuenta.setSaldoCreditos(dto.getSaldoCreditos());
        cuenta.setCuentaMercadoPagoId(dto.getCuentaMercadoPagoId());
        cuenta.setKmRecorridosMes(dto.getKmRecorridosMes());
        cuenta.setFechaRenovacionCupo(dto.getFechaRenovacionCupo());
        return cuenta;
    }
}