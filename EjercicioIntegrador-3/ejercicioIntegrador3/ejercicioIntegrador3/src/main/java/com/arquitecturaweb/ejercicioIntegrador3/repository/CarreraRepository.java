package com.arquitecturaweb.ejercicioIntegrador3.repository;

import com.arquitecturaweb.ejercicioIntegrador3.entity.Carrera;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface CarreraRepository extends JpaRepository<Carrera, Long> {
}
