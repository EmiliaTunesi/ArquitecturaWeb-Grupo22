package com.trabajointegrador.microserviciousuario.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ViajeDTO {
    private Long idCuenta;
    private Long idUsuario;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Double kilometros;
}
