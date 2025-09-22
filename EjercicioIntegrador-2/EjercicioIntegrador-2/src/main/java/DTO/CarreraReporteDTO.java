package DTO;

public class CarreraReporteDTO {
        private int inscriptos;
        private int egresados;

        public CarreraReporteDTO(int inscriptos){
            this.inscriptos = inscriptos;
            this.egresados = 0;
        }

        public void setEgresados(int egresados) {
            this.egresados = egresados;
        }

        @Override
        public String toString() {
            return "inscriptos = " + inscriptos +
                    ", egresados = " + egresados;
        }

}
