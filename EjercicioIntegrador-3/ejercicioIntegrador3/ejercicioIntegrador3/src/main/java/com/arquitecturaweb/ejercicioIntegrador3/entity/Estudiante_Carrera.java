package com.arquitecturaweb.ejercicioIntegrador3.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;


@Entity
@Getter
@Setter
public class Estudiante_Carrera implements Serializable {

    @EmbeddedId
    private Estudiante_Carrera_pk pk;

    @ManyToOne
    @JoinColumn(name = "estudiante_id")
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "carrera_id")
    private Carrera carrera;

    @Column
    private Integer anio_inicio;

    @Column
    private Integer anio_fin;

    @Column
    private int antiguedad;

    public Estudiante_Carrera(Estudiante ee, Carrera cc){
        this.pk = new Estudiante_Carrera_pk(ee.getId(),cc.getId_carrera());
        this.estudiante = ee;
        this.carrera = cc;
    }

    public Estudiante_Carrera() {

    }

    public Estudiante_Carrera(Estudiante estudiante, Carrera carrera, int anioInicio, int anioFin, int antiguedad) {
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.anio_inicio = anioInicio;
        this.anio_fin = anioFin;
        this.antiguedad = antiguedad;
        this.pk = new Estudiante_Carrera_pk(estudiante.getId(), carrera.getId_carrera());
    }

    public String getId() {
        return this.pk.toString();
    }

}
