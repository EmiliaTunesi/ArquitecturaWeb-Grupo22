package com.arquitecturaweb.ejercicioIntegrador3.repository;


import com.arquitecturaweb.ejercicioIntegrador3.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {

    Optional<Estudiante> findByLU(int lu);
}
