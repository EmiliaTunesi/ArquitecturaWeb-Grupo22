package com.arquitecturaweb.ejercicioIntegrador3.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id_carrera;

    @Column
    private String nombre;

    @Column
    private int duracion;

    @OneToMany(mappedBy = "carrera")
    @JsonIgnore
    private List<Estudiante_Carrera> estudiantes;
}
