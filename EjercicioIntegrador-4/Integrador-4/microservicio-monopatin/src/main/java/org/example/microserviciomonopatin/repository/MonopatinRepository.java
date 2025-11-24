package org.example.microserviciomonopatin.repository;

import org.example.microserviciomonopatin.entity.MonopatinEntity;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface MonopatinRepository extends MongoRepository<MonopatinEntity, String> {

}