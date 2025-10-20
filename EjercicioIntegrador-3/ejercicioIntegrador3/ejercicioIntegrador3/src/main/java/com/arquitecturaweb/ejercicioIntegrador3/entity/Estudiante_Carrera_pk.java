package com.arquitecturaweb.ejercicioIntegrador3.entity;

import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class Estudiante_Carrera_pk implements Serializable {

    private Long id_carrera;
    private Long id_estudiante;

    public Estudiante_Carrera_pk(Long id_carrera,Long id_estudiante) {
        this.id_carrera = id_carrera;
        this.id_estudiante = id_estudiante;
    }

    public Estudiante_Carrera_pk() {

    }

    @Override
    public String toString() {
        return id_carrera + "-" +id_estudiante;
    }
}