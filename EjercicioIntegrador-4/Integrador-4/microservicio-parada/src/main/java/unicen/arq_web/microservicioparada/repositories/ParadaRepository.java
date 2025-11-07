package unicen.arq_web.microservicioparada.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import unicen.arq_web.microservicioparada.dto.ParadaDTO;
import unicen.arq_web.microservicioparada.entities.Parada;


@Repository
public interface ParadaRepository extends JpaRepository<Parada,Integer> {
    @Query("SELECT '*' FROM Parada WHERE id = :id")
    public ParadaDTO findById(@Param("id") Long id);
}
