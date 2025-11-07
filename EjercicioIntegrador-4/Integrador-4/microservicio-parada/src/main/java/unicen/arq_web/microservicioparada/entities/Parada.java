package unicen.arq_web.microservicioparada.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "parada")
@Getter
@Setter
public class Parada {
    @Id
    @Column (name = "id")
    private Integer id;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    @Column(name = "activa", nullable = false)
    private Boolean activa = false;

    @Convert(disableConversion = true)
    @Column(name = "fecha_alta")
    private LocalDate fechaAlta;


}
