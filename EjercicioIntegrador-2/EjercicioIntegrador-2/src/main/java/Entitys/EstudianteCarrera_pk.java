package Entitys;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class EstudianteCarrera_pk implements Serializable {

        private int id_carrera;
        private int id_estudiante;
        public EstudianteCarrera_pk(int id_carrera, int id_estudiante) {
            this.id_carrera = id_carrera;
            this.id_estudiante = id_estudiante;
        }

        public EstudianteCarrera_pk() {
        }
}
