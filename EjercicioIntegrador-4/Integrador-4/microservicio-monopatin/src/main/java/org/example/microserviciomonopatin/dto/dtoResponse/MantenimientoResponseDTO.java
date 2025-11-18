package org.example.microserviciomonopatin.dto.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MantenimientoResponseDTO {

    private String id;
    private String monopatinId;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private String descripcion;
    private String tipoMantenimiento;
    private String estadoMantenimiento;
}
