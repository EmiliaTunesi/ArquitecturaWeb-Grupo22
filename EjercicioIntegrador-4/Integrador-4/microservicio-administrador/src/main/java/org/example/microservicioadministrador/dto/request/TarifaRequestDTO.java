package org.example.microservicioadministrador.dto.request;

import lombok.*;
import org.example.microservicioadministrador.entity.tipoTarifa;

import java.time.LocalDate;
@Data
@NoArgsConstructor
@Getter
@Setter
public class TarifaRequestDTO {
    private String nombre;
    private Double precio_min;
    private tipoTarifa tipo;
    private Integer tiempoEspera;
    private LocalDate vigenteDesde;
    private LocalDate vigenteHasta;
}
