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
    /*
     // Para el método findAll() del service
    List<Tarifa> findAll();

    // Para el método findById() del service
    Optional<Tarifa> findById(Long id);

    // Para los métodos save() y update() del service
    Tarifa save(Tarifa tarifa);

    // Para el método delete() del service
    void deleteById(Long id);
     */
    Optional<Tarifa> findByTipo(tipoTarifa tipo);

    @Query(value = """
        SELECT * 
        FROM tarifa 
        WHERE tipo = 'PROMOCION' 
        AND :fechaActual BETWEEN vigente_desde AND vigente_hasta
        ORDER BY vigente_desde DESC 
        LIMIT 1
        """,
            nativeQuery = true)
    Optional<Tarifa> findTarifaPromocionalVigente(@Param("fecha") LocalDate fecha);

}