package com.example.assistantapi.repository;

import com.example.assistantapi.entity.PearsonBibliotecaBookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PearsonRepository extends MongoRepository<PearsonBibliotecaBookEntity, String> {

    @Query("{$text: {$search: ?0}}")
    List<PearsonBibliotecaBookEntity> findDocumentByTitle(String title);

    @Query("{$text: {$search: ?0}}")
    List<PearsonBibliotecaBookEntity> findDocumentByAutor(String autor);

}
