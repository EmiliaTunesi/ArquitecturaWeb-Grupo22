package org.example.microserviciomonopatin.dto.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.microserviciomonopatin.utils.EstadoMonopatin;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonopatinResponseDTO {
    private String id;
    private EstadoMonopatin estado;
    private Double latitudActual;
    private Double longitudActual;
    private Double kilometrosTotales;
    private Double tiempoUsoTotal;
    private Double tiempoPausaTotal;
    private LocalDate fechaAlta;
}
