package com.trabajointegrador.microserviciousuario.dto;

import java.time.LocalDate;

public class CuentaDTO {

    private String numeroIdentificador;
    private LocalDate fechaAlta;
    private String tipoCuenta;

    public CuentaDTO() {}

    public CuentaDTO(String numeroIdentificador, LocalDate fechaAlta, String tipoCuenta) {
        this.numeroIdentificador = numeroIdentificador;
        this.fechaAlta = fechaAlta;
        this.tipoCuenta = tipoCuenta;
    }

    public LocalDate getFechaAlta() {
        return fechaAlta;
    }

    public void setFechaAlta(LocalDate fechaAlta) {
        this.fechaAlta = fechaAlta;
    }

    public String getNumeroIdentificador() {
        return numeroIdentificador;
    }

    public void setNumeroIdentificador(String numeroIdentificador) {
        this.numeroIdentificador = numeroIdentificador;
    }

    public String getTipoCuenta() {
        return tipoCuenta;
    }

    public void setTipoCuenta(String tipoCuenta) {
        this.tipoCuenta = tipoCuenta;
    }
}
