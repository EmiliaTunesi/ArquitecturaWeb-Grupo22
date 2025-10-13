package com.arquitecturaweb.ejercicioIntegrador3.repository;


import com.arquitecturaweb.ejercicioIntegrador3.entity.Estudiante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository("EstudianteRepository")
public interface EstudianteRepository extends JpaRepository<Estudiante, Long> {
}
