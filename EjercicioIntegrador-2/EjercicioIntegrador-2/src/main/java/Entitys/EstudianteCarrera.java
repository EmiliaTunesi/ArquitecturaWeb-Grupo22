package Entitys;

import javax.persistence.*;
import java.io.Serializable;

@Entity

public class EstudianteCarrera implements Serializable {
    @EmbeddedId
    private EstudianteCarrera_pk pk;

    @ManyToOne
    @MapsId("id_estudiante")
    private Estudiante estudiante;

    @ManyToOne
    @MapsId("id_carrera")
    private Carrera carrera;

    @Column
    private int anio_inicio;
    @Column
    private int anio_fin;
    @Column
    private int antiguedad;

    public EstudianteCarrera() {}

    public EstudianteCarrera(Estudiante e, Carrera c, int anio_inicio, int anio_fin, int antiguedad) {
        this.estudiante = e;
        this.carrera=c;
        this.anio_inicio = anio_inicio;
        this.anio_fin = anio_fin;
        this.antiguedad = antiguedad;
        this.pk = new EstudianteCarrera_pk(c.getId_carrera(),e.getDni());
    }
}