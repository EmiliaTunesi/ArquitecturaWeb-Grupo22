package com.trabajointegrador.microserviciousuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUsoDTO {
    private String nombreUsuario;
    private Double kilometros;
    private Long cantidadViajes;
    private List<String> otrosUsuarios;
}