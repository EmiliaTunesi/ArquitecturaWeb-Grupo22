package com.arquitecturaweb.ejercicioIntegrador3.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Estudiante_CarreraResponseDTO {

    private Integer id;
    private Long estudianteId;
    private Long carreraId;
    private LocalDate anio_inicio;
    private LocalDate anio_fin;
    private Integer antiguedad;
}
