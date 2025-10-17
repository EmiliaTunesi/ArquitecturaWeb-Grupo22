package com.arquitecturaweb.ejercicioIntegrador3.repository;

import com.arquitecturaweb.ejercicioIntegrador3.dto.response.CarreraConInscriptosDTO;
import com.arquitecturaweb.ejercicioIntegrador3.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {

    @Query("SELECT new com.arquitecturaweb.ejercicioIntegrador3.dto.response.CarreraConInscriptosDTO" +
            "(c.id_carrera, c.nombre, COUNT(ec.estudiante)) " +
            "FROM Estudiante_Carrera ec " +
            "JOIN ec.carrera c " +
            "GROUP BY c.id_carrera, c.nombre " +
            "HAVING COUNT(ec.estudiante) > 0 " +
            "ORDER BY COUNT(ec.estudiante) DESC")
    List<CarreraConInscriptosDTO> findCarrerasConCantidadInscriptos();
}
