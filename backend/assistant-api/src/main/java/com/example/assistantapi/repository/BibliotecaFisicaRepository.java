package com.example.assistantapi.repository;

import com.example.assistantapi.entity.FisicaBookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BibliotecaFisicaRepository extends MongoRepository<FisicaBookEntity, String> {
}
