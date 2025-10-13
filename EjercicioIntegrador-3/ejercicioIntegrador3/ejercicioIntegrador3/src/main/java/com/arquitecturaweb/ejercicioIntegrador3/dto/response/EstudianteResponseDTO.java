package com.arquitecturaweb.ejercicioIntegrador3.dto.response;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstudianteResponseDTO {

    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private int dni;
    private String genero;
    private int edad;
    private String ciudad_residencia;
    private int LU;
}
