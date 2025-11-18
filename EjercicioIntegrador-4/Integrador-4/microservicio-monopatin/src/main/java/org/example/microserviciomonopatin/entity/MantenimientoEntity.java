package org.example.microserviciomonopatin.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;


@Getter
@Setter
@Document(collection = "mantenimiento")
public class MantenimientoEntity {

    @Id
    private String id;  // Mongo usa String (ObjectId), no Long

    private String monopatinId;  // solo guardo el ID monopatín

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private String descripcion;

    private String tipoMantenimiento;
}