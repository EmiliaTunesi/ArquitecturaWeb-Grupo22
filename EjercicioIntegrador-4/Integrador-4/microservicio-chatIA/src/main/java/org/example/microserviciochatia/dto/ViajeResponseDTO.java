package org.example.microserviciochatia.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class ViajeResponseDTO {
    private Long id;
    private Long idUsuario;
    private Long idCuenta;
    private Long monopatinId;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Long paradaInicioId;
    private Long paradaFinId;
    private BigDecimal kmRecorridos;
    private Long tarifaId;
    private Integer tiempoTotalMinutos;
    private Integer pausaTotalMinutos;
    private BigDecimal costoTotal;
}
