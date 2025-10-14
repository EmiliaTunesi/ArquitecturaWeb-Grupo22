package com.arquitecturaweb.ejercicioIntegrador3.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarreraResponseDTO {

    private Long id_carrera;
    private String nombre;
    private Integer duracion;
}
