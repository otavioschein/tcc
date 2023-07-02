package com.example.assistantapi.service;

import com.example.assistantapi.entity.ElasticBookEntity;
import com.example.assistantapi.repository.ElasticsearchRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class AssistantService {

    private ElasticsearchRepository elasticsearchRepository;

    public List<String> getDocumentsByTitulo(String tituloValue) {
        var list = elasticsearchRepository.getDocumentsByTitulo(tituloValue);
        log.info("Titulo: {}", tituloValue);
        log.info("Books: {}", list);
        return list;
    }

    public ElasticBookEntity getDocumentByTitulo(String titulo) {
        var list = elasticsearchRepository.getDocumentByTitulo(titulo);
        log.info("Titulo: {}", titulo);
        log.info("Books: {}", list);
        return list;
    }

    public List<String> getDocumentsByAutor(String autor) {
        var list = elasticsearchRepository.getDocumentsByAutor(autor);
        log.info("Autor: {}", autor);
        log.info("Books: {}", list);
        return list;
    }

    public ElasticBookEntity getDocumentByAutor(String autorValue) {
        var list = elasticsearchRepository.getOneDocumentByAutor(autorValue);
        log.info("Autor: {}", autorValue);
        log.info("Books: {}", list);
        return list;
    }

}
