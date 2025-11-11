package org.example.microserviciomonopatin.repository;


import org.example.microserviciomonopatin.entity.MonopatinEntity;
import org.example.microserviciomonopatin.utils.EstadoMonopatin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonopatinRepository extends JpaRepository<MonopatinEntity, Long> {


    List<MonopatinEntity> findByEstado(EstadoMonopatin estado);

}
