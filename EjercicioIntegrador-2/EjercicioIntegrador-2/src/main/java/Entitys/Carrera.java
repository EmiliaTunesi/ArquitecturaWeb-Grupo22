package Entitys;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity

public class Carrera {
        @Id
        private int id_carrera;
        @Column
        private String nombre;
        @Column
        private int duracion;
        @OneToMany(mappedBy = "carrera")
        private List<EstudianteCarrera> estudiantes;

        public Carrera() {}
        public Carrera(int i,String nombre, int duracion) {
            this.id_carrera = i;
            this.nombre = nombre;
            this.duracion = duracion;
            this.estudiantes = new ArrayList<EstudianteCarrera>();
        }

        @Override
        public String toString() {
            return "Carrera{" +
                    "id_carrera=" + id_carrera +
                    ", nombre='" + nombre +
                    '}';
        }

        public int getId_carrera() {
            return id_carrera;
        }

        public String getNombre() {
            return nombre;
        }

}
