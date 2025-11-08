package org.example.microserviciomonopatin.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


@Getter
@Setter
@Entity
@Table(name = "mantenimiento")
public class MantenimientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "monopatin_id", nullable = false)
    private MonopatinEntity  monopatinId; // referencia al monopatín


    @Column(nullable = false)
    private LocalDate fechaInicio;

    @Column
    private LocalDate fechaFin;

    @Column
    private String descripcion;

    @Column(name = "tipo_mantenimiento")
    private String tipoMantenimiento;
}
