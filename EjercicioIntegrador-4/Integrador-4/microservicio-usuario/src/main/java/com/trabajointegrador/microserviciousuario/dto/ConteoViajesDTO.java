package com.trabajointegrador.microserviciousuario.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConteoViajesDTO {
    private long cantidadViajesUsuario;
    private long cantidadViajesCuenta;
}