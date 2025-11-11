package org.example.microservicioviaje.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "viaje")
@Getter
@Setter
public class viaje {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "id_usuario", nullable = false)
    private Long idUsuario;

    @Column(name = "id_cuenta", nullable = false)
    private Long idCuenta;

    @Column(name = "monopatin_id", nullable = false)
    private Long monopatinId;

    @Column(name = "fecha_hora_inicio", nullable = false)
    private LocalDateTime fechaInicio;

    @Column(name = "fecha_hora_fin")
    private LocalDateTime fechaFin;

    @Column(name = "parada_inicio_id", nullable = false)
    private Long paradaInicioId;

    @Column(name = "parada_fin_id")
    private Long paradaFinId;

    @Column(name = "km_recorridos")
    private BigDecimal kmRecorridos;

    @Column(name = "tarifa_id")
    private Long tarifaId;

    @Column(name = "tiempo_total_minutos")
    private Integer tiempoTotalMinutos;

    @Column(name = "pausa_total_minutos")
    private Integer pausaTotalMinutos;

    @Column(name = "costo_total")
    private BigDecimal costoTotal;

    public viaje() {
        this.fechaInicio = LocalDateTime.now();
    }
}
