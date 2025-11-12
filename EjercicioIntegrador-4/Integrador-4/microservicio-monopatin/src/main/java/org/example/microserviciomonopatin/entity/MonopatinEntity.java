package org.example.microserviciomonopatin.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.example.microserviciomonopatin.utils.EstadoMonopatin;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "monopatin")
public class MonopatinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoMonopatin estado;

    private Double latitudActual;
    private Double longitudActual;
    private Double kilometrosTotales;
    private Double tiempoUsoTotal;
    private Double tiempoPausaTotal;

    private LocalDate fechaAlta;
}
