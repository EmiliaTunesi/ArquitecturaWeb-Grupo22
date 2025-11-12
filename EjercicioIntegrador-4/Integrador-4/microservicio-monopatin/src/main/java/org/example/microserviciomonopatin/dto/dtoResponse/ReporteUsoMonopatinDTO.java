package org.example.microserviciomonopatin.dto.dtoResponse;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.microserviciomonopatin.utils.EstadoMonopatin;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReporteUsoMonopatinDTO {

    private Long id;
    private EstadoMonopatin estado;
    private Double kilometrosTotales;
    private Double tiempoUsoTotal;
    private Double tiempoPausaTotal;
    private boolean requiereMantenimiento;
}
