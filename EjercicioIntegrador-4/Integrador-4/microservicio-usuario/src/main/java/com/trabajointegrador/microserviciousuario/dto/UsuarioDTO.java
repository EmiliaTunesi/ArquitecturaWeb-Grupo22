package com.trabajointegrador.microserviciousuario.dto;

import com.trabajointegrador.microserviciousuario.utils.Rol;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class UsuarioDTO {

    private String nombreUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private LocalDateTime fechaRegistro;
    private Boolean activo;
    private Rol rol;

    private String password;

    public UsuarioDTO() {
    }



    public Boolean getActivo() {
        return activo;
    }


    @Override
    public String toString() {
        return "UsuarioDTO{nombreUsuario='" + nombreUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", activo=" + activo +
                '}';
    }
}
