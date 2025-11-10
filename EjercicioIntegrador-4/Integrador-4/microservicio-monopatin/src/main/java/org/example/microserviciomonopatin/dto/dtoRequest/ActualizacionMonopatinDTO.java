package org.example.microserviciomonopatin.dto.dtoRequest;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ActualizacionMonopatinDTO {
    private double kilometrosRecorridos;
    private double tiempoUso;
    private double tiempoPausa;
}
