package unicen.arq_web.microservicioparada.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import unicen.arq_web.microservicioparada.models.Monopatin;

import java.time.LocalDate;
import java.util.ArrayList;

@Entity
@Table(name = "parada")
@Getter
@Setter
public class Parada {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "monop_estacionados")
    private ArrayList<Monopatin> monopEstacionados;

    public ArrayList<Monopatin> getMonopEstacionados() {
        return new ArrayList<Monopatin>(this.monopEstacionados);
    }

    public void addMonopatin(Monopatin monopatin) {
        this.monopEstacionados.add(monopatin);
    }
}
