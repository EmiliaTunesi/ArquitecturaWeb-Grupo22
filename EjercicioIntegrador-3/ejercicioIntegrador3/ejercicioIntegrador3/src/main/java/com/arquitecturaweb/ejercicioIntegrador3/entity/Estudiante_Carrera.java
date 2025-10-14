package com.arquitecturaweb.ejercicioIntegrador3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Entity
@Getter
@Setter
public class Estudiante_Carrera {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private int id;

    @ManyToOne
    private Estudiante estudiante;

    @ManyToOne
    private Carrera carrera;

    @Column
    private LocalDate anio_inicio;

    @Column
    private LocalDate anio_fin;

    @Column
    private int antiguedad;


}
