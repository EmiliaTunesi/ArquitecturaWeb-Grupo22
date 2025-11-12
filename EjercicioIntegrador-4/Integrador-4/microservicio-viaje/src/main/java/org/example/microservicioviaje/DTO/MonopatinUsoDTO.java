package org.example.microservicioviaje.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MonopatinUsoDTO {
    private Double kmRecorridos;
    private Double tiempoUso; // tiempo_total_minutos
    private Double tiempoPausa; // pausa_total_minutos
}