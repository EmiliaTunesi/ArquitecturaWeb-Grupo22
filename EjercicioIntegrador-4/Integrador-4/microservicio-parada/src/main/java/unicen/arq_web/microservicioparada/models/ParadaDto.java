package unicen.arq_web.microservicioparada.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import unicen.arq_web.microservicioparada.entities.Parada;

import java.time.LocalDate;
import java.util.List;


@Getter
public class ParadaDto {

    private Pair<Double, Double> ubicacion;
    private boolean estaActiva;
    private LocalDate fechaAlta;
    private List<Long> idsMonopEstacionados;

    public ParadaDto(Parada p) {
        Double lat = p.getLatitud();
        Double longit = p.getLongitud();
        this.ubicacion = new Pair<>(lat, longit);
        this.estaActiva = p.getActiva();
        this.fechaAlta = p.getFechaAlta();
        this.idsMonopEstacionados = p.getIdsEstacionados();
    }
}
