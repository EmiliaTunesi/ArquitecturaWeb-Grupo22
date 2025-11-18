package org.example.microserviciomonopatin.repository;


import org.example.microserviciomonopatin.entity.MantenimientoEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MantenimientoRepository extends MongoRepository<MantenimientoEntity, String> {

    List<MantenimientoEntity> findByMonopatinId(String monopatinId);

    Optional<MantenimientoEntity> findByMonopatinIdAndFechaFinIsNull(String monopatinId);
}