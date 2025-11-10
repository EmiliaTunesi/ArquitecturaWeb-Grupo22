package org.example.microservicioviaje.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Parada {
    private Long id;
    private Double latitud;
    private Double longitud;
    private Boolean habilitada;
}
