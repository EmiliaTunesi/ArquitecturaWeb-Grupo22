package org.example.microservicioviaje.DTO;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class TarifaCalculadaDTO {
    private Long tarifaId;
    private BigDecimal costo; // El costo base que devuelve el servicio
}