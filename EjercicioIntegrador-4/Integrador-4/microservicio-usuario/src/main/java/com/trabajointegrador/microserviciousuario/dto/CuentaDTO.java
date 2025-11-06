package com.trabajointegrador.microserviciousuario.dto;

import java.time.LocalDate;

public class CuentaDTO {

    private String numeroIdentificador;
    private LocalDate fechaAlta;
    private String tipoCuenta;
    private boolean activa;
    private LocalDate fechaBaja;

    public CuentaDTO() {}

    public CuentaDTO(String numeroIdentificador, LocalDate fechaAlta, String tipoCuenta) {
        this.numeroIdentificador = numeroIdentificador;
        this.fechaAlta = fechaAlta;
        this.tipoCuenta = tipoCuenta;
        this.activa = true;
    }

    // --- Getters y Setters ---
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

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public LocalDate getFechaBaja() {
        return fechaBaja;
    }

    public void setFechaBaja(LocalDate fechaBaja) {
        this.fechaBaja = fechaBaja;
    }
}

