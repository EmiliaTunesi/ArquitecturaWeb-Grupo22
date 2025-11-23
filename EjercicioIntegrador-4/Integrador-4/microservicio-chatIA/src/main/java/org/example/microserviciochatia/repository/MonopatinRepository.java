package org.example.microserviciochatia.repository;

import org.example.microserviciochatia.model.Monopatin;
import org.example.microserviciochatia.util.EstadoMonopatin;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MonopatinRepository extends MongoRepository<Monopatin, String> {
    List<Monopatin> findByEstado(EstadoMonopatin estado);
}
