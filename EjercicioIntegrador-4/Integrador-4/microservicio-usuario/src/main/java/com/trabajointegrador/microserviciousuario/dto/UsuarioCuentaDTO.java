package com.trabajointegrador.microserviciousuario.dto;

import java.time.LocalDate;

public class UsuarioCuentaDTO {

    private String nombreUsuario;
    private String numeroCuenta;
    private boolean activa;
    private LocalDate fechaVinculacion;

    public UsuarioCuentaDTO() {}

    public UsuarioCuentaDTO(String nombreUsuario, String numeroCuenta, boolean activa, LocalDate fechaVinculacion) {
        this.nombreUsuario = nombreUsuario;
        this.numeroCuenta = numeroCuenta;
        this.activa = activa;
        this.fechaVinculacion = fechaVinculacion;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }

    public boolean isActiva() {
        return activa;
    }

    public void setActiva(boolean activa) {
        this.activa = activa;
    }

    public LocalDate getFechaVinculacion() {
        return fechaVinculacion;
    }

    public void setFechaVinculacion(LocalDate fechaVinculacion) {
        this.fechaVinculacion = fechaVinculacion;
    }
}
