package com.trabajointegrador.microserviciousuario.dto;

import java.util.Date;

public class UsuarioDTO {

    private String nombre_usuario;
    private String nombre;
    private String email;
    private String telefono;
    private Date fecha_registro;

    public UsuarioDTO() {}

    public UsuarioDTO(String nombre_usuario, String nombre, String email, String telefono, Date fecha_registro) {
        this.nombre_usuario = nombre_usuario;
        this.nombre = nombre;
        this.email = email;
        this.telefono = telefono;
        this.fecha_registro = fecha_registro;
    }

    public String getNombre_usuario() { return nombre_usuario; }
    public void setNombre_usuario(String nombre_usuario) { this.nombre_usuario = nombre_usuario; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public Date getFecha_registro() { return fecha_registro; }
    public void setFecha_registro(Date fecha_registro) { this.fecha_registro = fecha_registro; }
}
