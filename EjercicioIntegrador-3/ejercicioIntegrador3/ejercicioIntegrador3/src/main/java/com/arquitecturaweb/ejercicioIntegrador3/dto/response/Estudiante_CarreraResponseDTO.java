package com.arquitecturaweb.ejercicioIntegrador3.dto.response;

import com.arquitecturaweb.ejercicioIntegrador3.entity.Estudiante_Carrera;
import com.arquitecturaweb.ejercicioIntegrador3.entity.Estudiante_Carrera_pk;
import jakarta.persistence.criteria.CriteriaBuilder;
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

    private String id;
    private Long estudianteId;
    private Long carreraId;
    private Integer anio_inicio;
    private Integer anio_fin;
    private Integer antiguedad;
}
