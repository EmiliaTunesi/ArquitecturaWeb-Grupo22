package com.arquitecturaweb.ejercicioIntegrador3.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
public class Carrera {

    @Id
    @Column
    private Long id_carrera;

    @Column
    private String nombre;

    @Column
    private int duracion;

    @OneToMany(mappedBy = "carrera")
    @JsonIgnore
    private List<Estudiante_Carrera> estudiantes;

    public Carrera(long idCarrera, String nombre, int duracion) {
        this.id_carrera = idCarrera;
        this.nombre = nombre;
        this.duracion = duracion;
    }
    public Carrera(){

    }
}
