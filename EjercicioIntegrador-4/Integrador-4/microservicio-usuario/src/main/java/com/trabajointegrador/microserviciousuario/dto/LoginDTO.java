package com.trabajointegrador.microserviciousuario.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class LoginDTO {


    @NotNull( message = "El usuario es un campo requerido." )
    @NotEmpty( message = "El usuario es un campo requerido." )
    private String username;

    @NotNull( message = "La contraseña es un campo requerido." )
    @NotEmpty( message = "La contraseña es un campo requerido." )
    private String password;

    public String toString(){
        return "Username: " + username + ", Password: [FORBIDDEN] ";
    }
}
