package org.example.microservicioadministrador.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MonopatinReportKmDTO {
    private Long idMonopatin;
    private Double kilometrosTotales;
    private Double kilometrosConPausas;   // opcional, si querés mostrar ambos
    private Double kilometrosSinPausas;   // opcional
    private Long tiempoUso;               // minutos totales de uso
    private Long tiempoPausa;             // minutos en pausa
    private LocalDateTime fechaGeneracion;
    private Boolean incluyePausas;        // el flag que marcás según el parámetro
}
/*LA PAUSA SE CALCULA ACA O EN EL OTRO MICRO?
{
  "idMonopatin": 5,
  "kilometrosTotales": 12.5,
  "kilometrosConPausas": 15.0,
  "kilometrosSinPausas": 12.5,
  "tiempoUso": 35,
  "tiempoPausa": 10,
  "fechaGeneracion": "2025-11-05T10:15:30",
  "incluyePausas": false
}
 */
