package com.arquitecturaweb.ejercicioIntegrador3.repository;

import com.arquitecturaweb.ejercicioIntegrador3.entity.Estudiante_Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface Estudiante_CarreraRepository extends JpaRepository<Estudiante_Carrera, Long> {
}
