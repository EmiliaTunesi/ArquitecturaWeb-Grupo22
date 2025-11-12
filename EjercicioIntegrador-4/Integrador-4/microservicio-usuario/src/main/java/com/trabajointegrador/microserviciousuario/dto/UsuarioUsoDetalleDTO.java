package com.trabajointegrador.microserviciousuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioUsoDetalleDTO {
    private Long usuarioId;
    private String nombreUsuario;
    private Long cantidadViajes;
}