package org.example.microservicioviaje.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Monopatin {
    private Long id;
    private String estado;
    private Double kmRecorridos;
    private Double tiempoUso;
    private Double pausaTotal;

}
