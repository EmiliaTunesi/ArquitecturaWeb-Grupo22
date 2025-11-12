package org.example.microserviciomonopatin.repository;


import org.example.microserviciomonopatin.entity.MantenimientoEntity;
import org.example.microserviciomonopatin.entity.MonopatinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MantenimientoRepository extends JpaRepository<MantenimientoEntity, Long> {

    List<MantenimientoEntity> findByMonopatinId(MonopatinEntity monopatin);
    Optional<MantenimientoEntity> findByMonopatinIdAndFechaFinIsNull(MonopatinEntity monopatin);
}
