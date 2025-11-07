package com.trabajointegrador.microserviciousuario.dto;

import java.time.LocalDate;

public class UsuarioCuentaDTO {

    private String nombreUsuario;
    private String numeroCuenta;
    private LocalDate fechaVinculacion;

    public UsuarioCuentaDTO() {}

    public UsuarioCuentaDTO(String nombreUsuario, String numeroCuenta, LocalDate fechaVinculacion) {
        this.nombreUsuario = nombreUsuario;
        this.numeroCuenta = numeroCuenta;
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

    public LocalDate getFechaVinculacion() {
        return fechaVinculacion;
    }

    public void setFechaVinculacion(LocalDate fechaVinculacion) {
        this.fechaVinculacion = fechaVinculacion;
    }
}
