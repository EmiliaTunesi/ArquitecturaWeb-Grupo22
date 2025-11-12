package unicen.arq_web.microservicioparada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import unicen.arq_web.microservicioparada.entities.Parada;

import java.util.ArrayList;
import java.util.Optional;


@Repository
public interface ParadaRepository extends JpaRepository<Parada,Integer> {

    Optional<Parada> findById(Integer id);

    ArrayList<Parada> findAll();

    void delete(Parada p);

    Parada save(Parada nueva);


}
