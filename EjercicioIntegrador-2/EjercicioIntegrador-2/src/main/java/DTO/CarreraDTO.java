package DTO;

import Entitys.Carrera;

public class CarreraDTO {

       private String nombre;
        private int cantidad;

        public CarreraDTO(Carrera carrera, int cantidad)  {
            this.cantidad = cantidad;
            this.nombre = carrera.getNombre();
        }

        @Override
        public String toString() {
            return  "\nnombre='" + nombre + '\'' +
                    ", inscriptos=" + cantidad;
        }
    }

