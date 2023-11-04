package com.example.assistantapi.repository;

import com.example.assistantapi.entity.MinhaBibliotecaBookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MinhaBibliotecaRepository extends MongoRepository<MinhaBibliotecaBookEntity, String> {
}
