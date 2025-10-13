package com.arquitecturaweb.ejercicioIntegrador3.entity;

import jakarta.persistence.*;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@Entity
@Getter
@Setter
public class Estudiante {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private long id;

    @Column
    private String nombre;

    @Column
    private String apellido;

    @Column
    private String email;

    @Column
    private int dni;

    @Column
    private String genero;

    @Column
    private int edad;

    @Column
    private String ciudad_residencia;

    @Column
    private int LU;



}
