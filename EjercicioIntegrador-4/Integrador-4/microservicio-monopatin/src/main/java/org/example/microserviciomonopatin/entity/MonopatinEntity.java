package org.example.microserviciomonopatin.entity;

import lombok.Getter;
import lombok.Setter;
import org.example.microserviciomonopatin.utils.EstadoMonopatin;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Getter
@Setter
@Document(collection = "monopatin")
public class MonopatinEntity {

    @Id
    private String id;  // Mongo genera un ObjectId como String

    private EstadoMonopatin estado;

    private Double latitudActual;
    private Double longitudActual;

    private Double kilometrosTotales;
    private Double tiempoUsoTotal;
    private Double tiempoPausaTotal;

    private LocalDate fechaAlta;
}