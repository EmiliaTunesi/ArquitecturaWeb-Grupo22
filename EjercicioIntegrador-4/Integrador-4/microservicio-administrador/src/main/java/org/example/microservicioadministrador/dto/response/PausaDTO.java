package org.example.microservicioadministrador.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PausaDTO {
    private Long id;
    private Long viaje_id;
    private LocalDateTime fecha_hora_inicio;
    private LocalDateTime fecha_hora_fin;
    private Integer duracion_minutos;
}