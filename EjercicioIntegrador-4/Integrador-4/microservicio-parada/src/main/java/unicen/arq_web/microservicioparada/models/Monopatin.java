package unicen.arq_web.microservicioparada.models;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
public class Monopatin {

    public enum EstadoMonopatin {
        DISPONIBLE, EN_USO, EN_MANTENIMIENTO, FUERA_DE_SERVICIO
    }

    private EstadoMonopatin estado;
    private Double latitud;
    private Double longitud;
    private Double kilometrosTotales;
    private Double tiempoUsoTotal;
    private Double tiempoPausaTotal;
    private LocalDate fechaAlta;
}
