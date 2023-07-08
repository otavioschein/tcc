package com.example.assistantapi.service;

import com.example.assistantapi.mapper.PearsonMapper;
import com.example.assistantapi.response.MinhaBibliotecaResponse;
import com.example.assistantapi.mapper.MinhaBibliotecaMapper;
import com.example.assistantapi.repository.ElasticsearchRepository;
import com.example.assistantapi.response.PearsonResponse;
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

    public List<MinhaBibliotecaResponse> getDocumentsByAutorMinhaBiblioteca(String autor) {
        return elasticsearchRepository.minhaBibliotecaGetDocumentsByAutor(autor).stream()
                .filter(hits -> Objects.nonNull(hits.source()))
                .map(hit -> MinhaBibliotecaMapper.mapEntityToResponse(hit.source()))
                .toList();
    }

    public List<PearsonResponse> getDocumentsByTituloPearsonBiblioteca(String titulo) {
        return elasticsearchRepository.pearsonGetDocumentsByTitulo(titulo).stream()
                .filter(hits -> Objects.nonNull(hits.source()))
                .map(hit -> PearsonMapper.mapEntityToResponse(hit.source()))
                .toList();

    }

    public List<PearsonResponse> getDocumentsByAutorPearsonBiblioteca(String autor) {
        return elasticsearchRepository.pearsonGetDocumentsByAutor(autor).stream()
                .filter(hit -> Objects.nonNull(hit.source()))
                .map(hit -> PearsonMapper.mapEntityToResponse(hit.source()))
                .toList();
    }

}
