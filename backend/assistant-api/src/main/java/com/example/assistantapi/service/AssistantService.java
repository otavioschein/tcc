package com.example.assistantapi.service;

import com.example.assistantapi.entity.MinhaBibliotecaBookEntity;
import com.example.assistantapi.entity.PearsonBibliotecaBookEntity;
import com.example.assistantapi.mapper.MinhaBibliotecaMapper;
import com.example.assistantapi.mapper.PearsonMapper;
import com.example.assistantapi.repository.BookWiseRepository;
import com.example.assistantapi.response.FisicaResponse;
import com.example.assistantapi.response.MinhaBibliotecaResponse;
import com.example.assistantapi.response.PearsonResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

@Slf4j
@Service
@AllArgsConstructor
public class AssistantService {

    private final BookWiseRepository bookWiseRepository;

    //TODO
    public List<FisicaResponse> getDocumentsByTituloBibliotecaFisica(String titulo) {
//        return elasticsearchRepository.fisicaGetDocumentsByTitulo(titulo).stream()
//                .filter(hits -> Objects.nonNull(hits.source()))
//                .map(hit -> FisicaMapper.mapEntityToResponse(hit.source()))
//                .toList();
        return List.of();
    }

    public List<FisicaResponse> getSimilarDocumentsByTituloBibliotecaFisica(String titulo) {
//        return elasticsearchRepository.fisicaGetSimilarDocumentsByTitulo(titulo).stream()
//                .filter(hits -> Objects.nonNull(hits.source()))
//                .map(hit -> FisicaMapper.mapEntityToResponse(hit.source()))
//                .toList();
        return List.of();
    }

    public List<FisicaResponse> getDocumentsByAutorBibliotecaFisica(String autor) {
//        return elasticsearchRepository.fisicaGetDocumentsByAutor(autor).stream()
//                .filter(hit -> Objects.nonNull(hit.source()))
//                .map(hit -> FisicaMapper.mapEntityToResponse(hit.source()))
//                .toList();
        return List.of();
    }

    public List<FisicaResponse> getSimilarDocumentsByAutorBibliotecaFisica(String autor) {
//        return elasticsearchRepository.fisicaGetSimilarDocumentsByAutor(autor).stream()
//                .filter(hit -> Objects.nonNull(hit.source()))
//                .map(hit -> FisicaMapper.mapEntityToResponse(hit.source()))
//                .toList();
        return List.of();
    }

    //TODO
    public List<PearsonResponse> getDocumentsByTituloPearsonBiblioteca(String titulo) {
        var regexPattern = getRegexPattern(titulo);
        return bookWiseRepository.buscarLivrosPearsonBibliotecaPorTermo(titulo, PearsonBibliotecaBookEntity.class)
                .stream()
                .filter(livro -> regexPattern.matcher(livro.getTitulo()).find())
                .map(PearsonMapper::mapEntityToResponse)
                .toList();
    }

    public List<PearsonResponse> getSimilarDocumentsByTituloPearsonBiblioteca(String titulo) {
        return bookWiseRepository.buscarLivrosPearsonBibliotecaPorTermo(titulo, PearsonBibliotecaBookEntity.class)
                .stream()
                .limit(5L)
                .map(PearsonMapper::mapEntityToResponse)
                .toList();
    }

    public List<PearsonResponse> getDocumentsByAutorPearsonBiblioteca(String autor) {
        var regexPattern = getRegexPattern(autor);
        return bookWiseRepository.buscarLivrosPearsonBibliotecaPorTermo(autor, PearsonBibliotecaBookEntity.class)
                .stream()
                .filter(livro -> regexPattern.matcher(livro.getAutor()).find())
                .map(PearsonMapper::mapEntityToResponse)
                .toList();
    }


    public List<MinhaBibliotecaResponse> getDocumentsByTituloMinhaBiblioteca(String titulo) {
        var regexPattern = getRegexPattern(titulo);
        return bookWiseRepository.buscarLivrosPearsonBibliotecaPorTermo(titulo, MinhaBibliotecaBookEntity.class)
                .stream()
                .filter(livro -> regexPattern.matcher(livro.getTitulo()).find())
                .map(MinhaBibliotecaMapper::mapEntityToResponse)
                .toList();
    }

    public List<MinhaBibliotecaResponse> getSimilarDocumentsByTituloMinhaBiblioteca(String titulo) {
        return bookWiseRepository.buscarLivrosPearsonBibliotecaPorTermo(titulo, MinhaBibliotecaBookEntity.class)
                .stream()
                .map(MinhaBibliotecaMapper::mapEntityToResponse)
                .toList();
    }

    public List<MinhaBibliotecaResponse> getDocumentsByAutorMinhaBiblioteca(String autor) {
        var regexPattern = getRegexPattern(autor);
        log.info("Regex pattern: {}", regexPattern);
        return bookWiseRepository.buscarLivrosPearsonBibliotecaPorTermo(autor, MinhaBibliotecaBookEntity.class)
                .stream()
                .filter(livro -> regexPattern.matcher(livro.getAutor()).find())
                .map(MinhaBibliotecaMapper::mapEntityToResponse)
                .toList();
    }

    private Pattern getRegexPattern(String term) {
        List<String> splittedTerm = Arrays.stream(term.split(" ")).toList();
        var regex = String.join(".*?", splittedTerm);
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

}
