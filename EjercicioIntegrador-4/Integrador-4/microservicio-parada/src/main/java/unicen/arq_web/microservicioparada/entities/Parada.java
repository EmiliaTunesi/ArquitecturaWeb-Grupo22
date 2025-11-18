package unicen.arq_web.microservicioparada.entities;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "Parada")
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
    private Boolean activa;

    @Convert(disableConversion = true)
    @Column(name = "fecha_alta", updatable = false)
    private LocalDate fechaAlta;

    @ElementCollection
    @CollectionTable(name = "estacionados", joinColumns = @JoinColumn(name = "id_parada"))
    @Column(name = "id_monopatin")
    private List<Long> idsMonopEstac = new ArrayList<>();


    public Parada(){}

    public void setActiva(Boolean activa) {
        this.activa = activa;
        if (Boolean.TRUE.equals(activa) && this.fechaAlta == null) {
            this.fechaAlta = LocalDate.now();
        }
    }

    @PrePersist
    public void darDeAlta() {
        if (Boolean.TRUE.equals(this.activa) && this.fechaAlta == null) {
            this.fechaAlta = LocalDate.now();
        }
    }

    public boolean agregarMonopatin(Long idMonopatin){
        if (!this.idsMonopEstac.contains(idMonopatin)) {
            return this.idsMonopEstac.add(idMonopatin);
        }
        return false;
    }

    public boolean quitarMonopatin(Long idMonopatin){
        if (this.idsMonopEstac.contains(idMonopatin)) {
            return this.idsMonopEstac.remove(idMonopatin);
        }
        return false;
    }

    public List<Long> getIdsEstacionados() {
        return new ArrayList<>(this.idsMonopEstac);
    }

}
