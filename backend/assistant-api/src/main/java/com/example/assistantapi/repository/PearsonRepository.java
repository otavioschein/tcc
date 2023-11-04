package com.example.assistantapi.repository;

import com.example.assistantapi.entity.PearsonBibliotecaBookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PearsonRepository extends MongoRepository<PearsonBibliotecaBookEntity, String> {
}
