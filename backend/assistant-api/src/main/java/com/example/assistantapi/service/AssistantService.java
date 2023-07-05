package com.example.assistantapi.service;

import com.example.assistantapi.entity.MinhaBibliotecaResponse;
import com.example.assistantapi.mapper.MinhaBibliotecaMapper;
import com.example.assistantapi.repository.ElasticsearchRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@AllArgsConstructor
public class AssistantService {

    private final ElasticsearchRepository elasticsearchRepository;

    public List<MinhaBibliotecaResponse> getDocumentsByTituloMinhaBiblioteca(String tituloValue) {
        return elasticsearchRepository.minhaBibliotecaGetDocumentsByTitulo(tituloValue).stream()
                .filter(hits -> Objects.nonNull(hits.source()))
                .map(hit ->  MinhaBibliotecaMapper.mapEntityToResponse(hit.source()))
                .toList();
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
