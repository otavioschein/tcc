package com.example.assistantapi.repository;

import com.example.assistantapi.entity.PearsonBibliotecaBookEntity;
import com.example.assistantapi.entity.cache.FisicaEntityCache;
import com.example.assistantapi.entity.cache.MinhaBibliotecaEntityCache;
import com.example.assistantapi.entity.cache.PearsonEntityCache;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.data.mongodb.core.mapping.Document;
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

    public FisicaEntityCache getCachedFisica(String key) {
        return mongoTemplate.findById(key, FisicaEntityCache.class);
    }

    public PearsonEntityCache getCachedPearson(String key) {
        return mongoTemplate.findById(key, PearsonEntityCache.class);
    }

    public MinhaBibliotecaEntityCache getCachedMb(String key) {
        return mongoTemplate.findById(key, MinhaBibliotecaEntityCache.class);
    }

    public void deleteObject(Object obj, String collectionName) {
        mongoTemplate.remove(obj, collectionName);
    }

    public void saveCacheMB(MinhaBibliotecaEntityCache entityCache) {
        mongoTemplate.save(entityCache);
    }

    public void saveCachePearson(PearsonEntityCache entityCache) {
        mongoTemplate.save(entityCache);
    }

    public void saveCacheFisica(FisicaEntityCache entityCache) {
        mongoTemplate.save(entityCache);
    }

    public void createTextIndexForCatalog(String collection) {
        TextIndexDefinition textIndex = new TextIndexDefinition.TextIndexDefinitionBuilder()
                .onField("titulo")
                .onField("autor")
                .build();

        // Substitua "catalogCollection" pelo nome da sua coleção
        mongoTemplate.indexOps(collection).ensureIndex(textIndex);
    }

}
