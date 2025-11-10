package org.example.microservicioadministrador.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Data

@Table(name = "tarifa")
public class Tarifa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El precio es obligatorio")
    private Double precio_min;

    @NotNull(message = "El tipo de tarifa es obligatorio")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private tipoTarifa tipo;

    @NotNull(message = "El tiempo es obligatorio")
    private Integer tiempoEspera;

    @NotNull(message = "la fecha de vigencia es obligatorio")
    private LocalDate vigenteDesde;

    private LocalDate vigenteHasta;

}
