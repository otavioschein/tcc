package com.example.assistantapi.repository;

import com.example.assistantapi.entity.cache.FisicaEntityCache;
import com.example.assistantapi.entity.cache.MinhaBibliotecaEntityCache;
import com.example.assistantapi.entity.cache.PearsonEntityCache;
import lombok.AllArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.index.TextIndexDefinition;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class BookWiseRepository {

    private final MongoTemplate mongoTemplate;

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
        mongoTemplate.indexOps(collection).ensureIndex(textIndex);
    }

}
