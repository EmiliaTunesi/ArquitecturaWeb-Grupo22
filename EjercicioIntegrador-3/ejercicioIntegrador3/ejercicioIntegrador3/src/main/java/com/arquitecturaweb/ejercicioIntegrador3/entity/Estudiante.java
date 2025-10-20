package com.arquitecturaweb.ejercicioIntegrador3.entity;

import jakarta.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
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
    private int dni;

    @Column
    private String genero;

    @Column
    private int edad;

    @Column
    private String ciudad_residencia;

    @Column
    private int LU;

    @OneToMany(mappedBy = "estudiante")
    @JsonIgnore
    private List<Estudiante_Carrera> carreras;


    public Estudiante(String nombre, String apellido, int dni, String genero, int edad, String ciudad, int nroLU) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.genero = genero;
        this.edad = edad;
        this.ciudad_residencia = ciudad;
        this.LU = nroLU;
    }
    public Estudiante(){

    }
}
