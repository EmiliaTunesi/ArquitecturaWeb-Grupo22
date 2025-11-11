package com.trabajointegrador.microserviciousuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioRankingDTO {
    private Long idUsuario;
    private String nombreUsuario;
    private long cantidadViajes;
}