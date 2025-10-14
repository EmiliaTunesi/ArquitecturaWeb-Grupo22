package com.arquitecturaweb.ejercicioIntegrador3.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteRequestDTO {


    private String nombre;
    private String apellido;
    private String email;
    private int dni;
    private String genero;
    private int edad;
    private String ciudad_residencia;
    private int lu;
}
