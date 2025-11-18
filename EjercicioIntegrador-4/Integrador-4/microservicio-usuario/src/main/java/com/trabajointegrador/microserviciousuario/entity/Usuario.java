package com.trabajointegrador.microserviciousuario.entity;
import com.trabajointegrador.microserviciousuario.utils.Rol;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;


@Getter
@Setter
@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre_usuario", nullable = false, unique = true)
    private String nombreUsuario;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private String apellido;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String telefono;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private Boolean activo;

    @Column(name = "rol")
    private Rol rol;

    @Column(name = "password", nullable = false)
    private String password;


    public Usuario() {}

    public Usuario(
            String nombreUsuario,
            String nombre,
            String apellido,
            String email,
            String telefono,
            LocalDateTime fechaRegistro,
            boolean activo,
            Rol rol,
            String password
    ) {
        this.nombreUsuario = nombreUsuario;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.fechaRegistro = fechaRegistro;
        this.activo = activo;
        this.rol = rol;
        this.password = password;
    }



    public boolean isActivo() {
        return activo;
    }





    @Override
    public String toString() {
        return "Usuario{id=" + id +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", email='" + email + '\'' +
                ", telefono='" + telefono + '\'' +
                ", fechaRegistro=" + fechaRegistro +
                ", activo=" + activo +
                '}';
    }
}

