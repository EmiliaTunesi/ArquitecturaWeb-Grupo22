package org.example.microservicioadministrador.dto.response;

import lombok.*;
import org.example.microservicioadministrador.entity.tipoTarifa;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class TarifaResponseDTO {
    private Long id;
    private Double precio_min;
}
