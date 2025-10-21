package com.arquitecturaweb.ejercicioIntegrador3.repository;


import com.arquitecturaweb.ejercicioIntegrador3.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findByDni(int dni);

    Optional<Estudiante> findByLU(int lu);
    List<Estudiante> findByGeneroIgnoreCase(String genero);

    //Busca los estudiantes de una carrera filtrados por ciudad
    @Query("""
        SELECT e
        FROM Estudiante e
        JOIN e.carreras ec
        JOIN ec.carrera c
        WHERE c.id_carrera = :carreraId
        AND e.ciudad_residencia = :ciudad
        """)
    List<Estudiante> findByCarreraCiudad(@Param("carreraId") int carreraId, @Param("ciudad") String ciudad);

}
