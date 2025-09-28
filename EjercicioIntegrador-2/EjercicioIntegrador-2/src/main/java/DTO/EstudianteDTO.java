package DTO;
import Entitys.Estudiante;

public class EstudianteDTO {

        private String nombre;
        private String apellido;
        private int LU;

        public EstudianteDTO(Estudiante estudiante){
            this.nombre = estudiante.getNombre();
            this.apellido = estudiante.getApellido();
            this.LU = estudiante.getNroLU() != null ? estudiante.getNroLU() : 0; // o algún valor por defecto

        }

        @Override
        public String toString() {
            return "\nLU = " + LU +
                    ", apellido = " + apellido +
                    ", nombre = " + nombre;
        }
 }
