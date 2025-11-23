package org.example.microserviciochatia.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.microserviciochatia.util.EstadoMonopatin;

import java.time.LocalDate;
@Setter
@Getter
public class MonopatinResponseDTO {
    private String id;
    private String estado;
    private double latitudActual;
    private double longitudActual;
    private Double kilometrosTotales;
    private Double tiempoUsoTotal;
    private Double tiempoPausaTotal;

    private LocalDate fechaAlta;
}
