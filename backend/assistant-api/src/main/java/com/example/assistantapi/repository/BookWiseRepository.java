package com.example.assistantapi.repository;

import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.TextCriteria;
import org.springframework.data.mongodb.core.query.TextQuery;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class BookWiseRepository {

    private final MongoTemplate mongoTemplate;

    public <T> List<T> buscarLivrosPearsonBibliotecaPorTermo(String searchTerm, Class<T> clazz) {
        TextCriteria criteria = TextCriteria.forDefaultLanguage().matching(searchTerm);
        Query query = TextQuery.queryText(criteria).sortByScore();
        return mongoTemplate.find(query, clazz);
    }

}
