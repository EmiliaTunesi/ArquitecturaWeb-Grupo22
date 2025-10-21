package com.arquitecturaweb.ejercicioIntegrador3.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Estudiante_CarreraRequestDTO {

    @NotNull(message = "El ID del estudiante es obligatorio")
    private int dni;

    @NotNull(message = "El ID de la carrera es obligatorio")
    private Long carreraId;

    @NotNull(message = "El año de inicio es obligatorio")
    private Integer anio_inicio;

    private Integer anio_fin;

    private Integer antiguedad;
}
