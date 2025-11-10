package org.example.microservicioadministrador.repository;

import feign.Param;
import org.example.microservicioadministrador.dto.response.TarifaResponseDTO;
import org.example.microservicioadministrador.entity.Tarifa;
import org.example.microservicioadministrador.entity.tipoTarifa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Repository
public interface TarifaRepository extends JpaRepository<Tarifa, Long> {

    @Query(value = """
    SELECT t 
    FROM Tarifa t 
    WHERE t.tipo = :tipo
    ORDER BY t.vigenteDesde DESC
    LIMIT 1
    """)
    Optional<Tarifa> findByTipo(@Param("tipo") tipoTarifa tipo);

    @Query(value = """    
SELECT *
FROM tarifa
WHERE tipo = 'PROMOCIONAL'
  AND vigente_desde <= CURRENT_DATE
  AND vigente_hasta >= CURRENT_DATE
UNION
SELECT *
FROM tarifa
WHERE tipo = 'NORMAL'
  AND vigente_hasta IS NULL
ORDER BY vigente_desde DESC
LIMIT 1;

        """, nativeQuery = true)
    Optional<Tarifa> findTarifaPromocionalVigente();
//devuelvo la tarifa mas reciente que esta vigente hoy


}