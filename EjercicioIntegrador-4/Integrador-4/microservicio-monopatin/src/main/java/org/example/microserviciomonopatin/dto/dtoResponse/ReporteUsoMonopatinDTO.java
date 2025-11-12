package org.example.microserviciomonopatin.dto.dtoResponse;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.microserviciomonopatin.utils.EstadoMonopatin;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL) // 🔹 Ignora los campos nulos en el JSON
public class ReporteUsoMonopatinDTO {

    private Long id;
    private EstadoMonopatin estado;
    private Double kilometrosTotales;
    private Double tiempoUsoTotal;
    private Double tiempoPausaTotal; // Se mostrará solo si no es null
    private boolean requiereMantenimiento;
}
