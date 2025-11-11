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
WHERE
  (
    tipo = 'PROMOCIONAL'
    AND vigente_desde <= now()
    AND (vigente_hasta IS NULL OR vigente_hasta > now())
  )
  OR
  (
    tipo = 'NORMAL'
    AND vigente_hasta IS NULL
  )
ORDER BY
  CASE
    WHEN tipo = 'PROMOCIONAL'
         AND vigente_desde <= now()
         AND (vigente_hasta IS NULL OR vigente_hasta > now())
    THEN 0
    ELSE 1
  END,
  vigente_desde DESC
LIMIT 1

        """, nativeQuery = true)
    Optional<Tarifa> findTarifaPromocionalVigente();
//devuelvo la tarifa mas reciente que esta vigente hoy


}