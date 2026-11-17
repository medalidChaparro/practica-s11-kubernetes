package com.agrotecno.backend.repository;

import com.agrotecno.backend.model.Cliente;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClienteRepository extends ReactiveMongoRepository<Cliente, String> {
}

