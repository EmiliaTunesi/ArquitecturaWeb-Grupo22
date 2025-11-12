package unicen.arq_web.microservicioparada.models;

import lombok.AllArgsConstructor;
import lombok.Getter;


import java.time.LocalDate;

@AllArgsConstructor
@Getter
public class Monopatin {

    private enum EstadoMonopatin {
        DISPONIBLE, EN_USO, EN_MANTENIMIENTO, FUERA_DE_SERVICIO
    }

    private Long id;
    private EstadoMonopatin estado;
    private Double latitudActual;
    private Double longitudActual;
    private Double kilometrosTotales;
    private Double tiempoUsoTotal;
    private Double tiempoPausaTotal;
    private LocalDate fechaAlta;


}
