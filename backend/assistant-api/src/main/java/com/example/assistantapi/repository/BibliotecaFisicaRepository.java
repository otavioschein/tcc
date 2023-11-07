package com.example.assistantapi.repository;

import com.example.assistantapi.entity.FisicaBookEntity;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BibliotecaFisicaRepository extends MongoRepository<FisicaBookEntity, String> {

    @Query("{$text: {$search: ?0}}")
    List<FisicaBookEntity> findDocumentByTitle(String title);

    @Query("{$text: {$search: ?0}}")
    List<FisicaBookEntity> findDocumentByAuthor(String author);

}
