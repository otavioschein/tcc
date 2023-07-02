package com.example.assistantapi.service;

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

    public List<String> getDocumentsByTituloMinhaBiblioteca(String tituloValue) {
        var list = elasticsearchRepository.minhaBibliotecaGetDocumentsByTitulo(tituloValue);
        log.info("Titulo: {}", tituloValue);
        log.info("Books: {}", list);
        return list;
    }

    public List<String> getDocumentsByAutorMinhaBiblioteca(String autor) {
        var list = elasticsearchRepository.minhaBibliotecaGetDocumentsByAutor(autor);
        log.info("Autor: {}", autor);
        log.info("Books: {}", list);
        return list;
    }

    public List<String> getDocumentsByTituloPearsonBiblioteca(String titulo) {
        var list = elasticsearchRepository.pearsonGetDocumentsByTitulo(titulo);
        log.info("Titulo: {}", titulo);
        log.info("Books: {}", list);
        return list;
    }

    public List<String> getDocumentsByAutorPearsonBiblioteca(String autor) {
        var list = elasticsearchRepository.pearsonGetDocumentsByAutor(autor);
        log.info("Autor: {}", autor);
        log.info("Books: {}", list);
        return list;
    }

}
