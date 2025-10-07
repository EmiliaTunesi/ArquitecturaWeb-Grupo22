package DTO;
import java.util.Map;
import java.util.TreeMap;

public class ReporteAnioDTO {

        String nombreCarrera;
        Map<Integer,CarreraReporteDTO>infoPorAnio;

        public ReporteAnioDTO(String nombreCarrera) {
            this.nombreCarrera = nombreCarrera;
            this.infoPorAnio = new TreeMap<>();
        }

        @Override
        public String toString() {
            return "\nCarrera = " + nombreCarrera + ", " +
                    "Informacion de alumnos por año = " + infoPorAnio;
        }

        public String getNombreCarrera() {
            return nombreCarrera;
        }

        public void setNombreCarrera(String nombreCarrera) {
            this.nombreCarrera = nombreCarrera;
        }

        public Map<Integer, CarreraReporteDTO> getInfoPorAnio() {
            return infoPorAnio;
        }

        public void setInfoPorAnio(Map<Integer, CarreraReporteDTO> infoPorAnio) {
            this.infoPorAnio = infoPorAnio;
        }

}
