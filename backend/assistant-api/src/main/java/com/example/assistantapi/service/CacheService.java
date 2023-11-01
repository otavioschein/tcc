package com.example.assistantapi.service;

import com.example.assistantapi.entity.FisicaBookEntity;
import com.example.assistantapi.entity.MinhaBibliotecaBookEntity;
import com.example.assistantapi.entity.PearsonBibliotecaBookEntity;
import com.example.assistantapi.entity.cache.FisicaEntityCache;
import com.example.assistantapi.entity.cache.MinhaBibliotecaEntityCache;
import com.example.assistantapi.entity.cache.PearsonEntityCache;
import com.example.assistantapi.repository.BookWiseRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CacheService {

    private final BookWiseRepository bookWiseRepository;

    private static final String MINHA_KEY = "MINHA@LIVROS_";
    private static final String PEARSON_KEY = "PEARSON@LIVROS_";
    private static final String FISICA_KEY = "FISICA@LIVROS_";

    public void putInCacheMB(String sessionId, List<MinhaBibliotecaBookEntity> mbList) {
        var cacheEntity = MinhaBibliotecaEntityCache.builder()
                .cacheId(getMBCacheKey(sessionId))
                .listMBBook(mbList)
                .build();
        bookWiseRepository.saveCacheMB(cacheEntity);
    }

    public void putInCachePearson(String sessionId, List<PearsonBibliotecaBookEntity> pearsonList) {
        var cacheEntity = PearsonEntityCache.builder()
                .cacheId(getPearsonCacheKey(sessionId))
                .listPearsonBook(pearsonList)
                .build();
        bookWiseRepository.saveCachePearson(cacheEntity);
    }

    public void putInCacheFisica(String sessionId, List<FisicaBookEntity> fisicaList) {
        var cacheEntity = FisicaEntityCache.builder()
                .cacheId(getFisicaCacheKey(sessionId))
                .listFisica(fisicaList)
                .build();
        bookWiseRepository.saveCacheFisica(cacheEntity);
    }

    public String getFisicaCacheKey(String sessionId) {
        return FISICA_KEY + sessionId;
    }
    public String getPearsonCacheKey(String sessionId) {
        return PEARSON_KEY + sessionId;
    }
    public String getMBCacheKey(String sessionId) {
        return MINHA_KEY + sessionId;
    }

}
