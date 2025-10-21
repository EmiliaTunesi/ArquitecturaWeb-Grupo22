package com.arquitecturaweb.ejercicioIntegrador3.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EstudianteResponseDTO {

    private long id;
    private String nombre;
    private String apellido;
    private int dni;
    private String genero;
    private int edad;
    private String ciudad_residencia;
    private int LU;
}
