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
    private int dni;
    private String genero;
    private int edad;
    private String ciudad_residencia;
    private int lu;


    public EstudianteRequestDTO(String nombre, String apellido, int dni, String genero, int edad, String ciudad_residencia) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.genero = genero;
        this.edad = edad;
        this.ciudad_residencia = ciudad_residencia;
    }
}
