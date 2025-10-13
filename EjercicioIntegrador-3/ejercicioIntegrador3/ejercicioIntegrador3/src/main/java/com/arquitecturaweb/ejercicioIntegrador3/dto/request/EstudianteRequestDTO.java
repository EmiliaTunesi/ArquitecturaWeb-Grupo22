package com.arquitecturaweb.ejercicioIntegrador3.dto.request;

import lombok.Getter;


@Getter
public class EstudianteRequestDTO {


    private long id;
    private String nombre;
    private String apellido;
    private String email;
    private int dni;
    private String genero;
    private int edad;
    private String ciudad_residencia;
    private int lu;
}
