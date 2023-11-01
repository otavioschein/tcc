package com.example.assistantapi.service;

import com.example.assistantapi.entity.FisicaBookEntity;
import com.example.assistantapi.entity.MinhaBibliotecaBookEntity;
import com.example.assistantapi.entity.PearsonBibliotecaBookEntity;
import com.example.assistantapi.mapper.AssistantMapper;
import com.example.assistantapi.mapper.FisicaMapper;
import com.example.assistantapi.mapper.MinhaBibliotecaMapper;
import com.example.assistantapi.mapper.PearsonMapper;
import com.example.assistantapi.repository.BookWiseRepository;
import com.example.assistantapi.response.AssistantResponse;
import com.example.assistantapi.response.LivroResponse;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.*;
import java.util.regex.Pattern;

@Slf4j
@Service
@AllArgsConstructor
public class AssistantService {

    private final CacheService cacheService;
    private final BookWiseRepository bookWiseRepository;
    private static final Set<String> stopWords = new HashSet<>(Arrays.asList("o", "os", "a", "as", "do", "dos", "da", "das", "de", "e"));

    private final String PEARSON_BIBLIOTECA = "Pearson Biblioteca";
    private final String FISICA_BIBLIOTECA = "Biblioteca Física";
    private final String MINHA_BIBLIOTECA = "Minha Biblioteca";

    public AssistantResponse getDocumentsByTituloBibliotecaFisica(String sessionId, String titulo) {
        boolean isCache = false;
        var listLivros = bookWiseRepository.buscarLivrosPearsonBibliotecaPorTermo(titulo, FisicaBookEntity.class)
                .stream()
                .limit(10)
                .filter(livro -> Objects.nonNull(livro.getTitulo()))
                .filter(livro -> validateBook(titulo, livro.getTitulo()))
                .toList();
        var listRefined = excludeEquals(listLivros);
        if (!listRefined.isEmpty() && listRefined.size() > 5) {
            cacheService.putInCacheFisica(sessionId, listRefined);
            isCache = true;
        }
        var livroResponseList = listRefined.stream()
                .filter(livro -> Objects.nonNull(livro.getTitulo()) && Objects.nonNull(livro.getNumeroDeChamada()))
                .limit(5)
                .map(FisicaMapper::mapEntityToResponse)
                .distinct()
                .toList();
        return AssistantMapper.mapToAssistantResponse(FISICA_BIBLIOTECA, isCache, livroResponseList);
    }

    public List<LivroResponse> getDocumentsByAutorBibliotecaFisica(String autor) {
        var regexPattern = getRegexPattern(autor);
        log.info("Regex pattern: {}", regexPattern);
        return bookWiseRepository.buscarLivrosPearsonBibliotecaPorTermo(autor, FisicaBookEntity.class)
                .stream()
                .filter(livro -> Objects.nonNull(livro.getAutor()))
                .filter(livro -> regexPattern.matcher(livro.getAutor()).find())
                .map(FisicaMapper::mapEntityToResponse)
                .toList();
    }


    public AssistantResponse getDocumentsByTituloPearsonBiblioteca(String sessionId, String titulo) {
        var isCache = false;
        var pearsonLivrosList = bookWiseRepository.buscarLivrosPearsonBibliotecaPorTermo(titulo, PearsonBibliotecaBookEntity.class)
                .stream()
                .limit(10)
                .filter(livro -> Objects.nonNull(livro.getTitulo()))
                .filter(livro -> validateBook(titulo, livro.getTitulo()))
                .toList();
        if (!pearsonLivrosList.isEmpty() && pearsonLivrosList.size() > 5) {
            cacheService.putInCachePearson(sessionId, pearsonLivrosList);
            isCache = true;
        }
        var livroResponseList = pearsonLivrosList
                .stream()
                .limit(5)
                .filter(livro -> Objects.nonNull(livro.getTitulo()))
                .map(PearsonMapper::mapEntityToResponse)
                .toList();
        return AssistantMapper.mapToAssistantResponse(PEARSON_BIBLIOTECA, isCache, livroResponseList);
    }

    public List<LivroResponse> getDocumentsByAutorPearsonBiblioteca(String autor) {
        var regexPattern = getRegexPattern(autor);
        log.info("Regex pattern: {}", regexPattern);
        return bookWiseRepository.buscarLivrosPearsonBibliotecaPorTermo(autor, PearsonBibliotecaBookEntity.class)
                .stream()
                .filter(livro -> Objects.nonNull(livro.getAutor()))
                .filter(livro -> regexPattern.matcher(livro.getAutor()).find())
                .map(PearsonMapper::mapEntityToResponse)
                .toList();
    }

    public AssistantResponse getDocumentsByTituloMinhaBiblioteca(String sessionId, String titulo) {
        var isCache = false;
        var bookList = bookWiseRepository.buscarLivrosPearsonBibliotecaPorTermo(titulo, MinhaBibliotecaBookEntity.class)
                .stream()
                .limit(10)
                .filter(livro -> Objects.nonNull(livro.getTitulo()))
                .filter(livro -> validateBook(titulo, livro.getTitulo()))
                .toList();
        if (!bookList.isEmpty() && bookList.size() > 5) {
            cacheService.putInCacheMB(sessionId, bookList);
            isCache = true;
        }
        var listResponse = bookList
                .stream()
                .limit(5)
                .filter(livro -> Objects.nonNull(livro.getTitulo()))
                .map(MinhaBibliotecaMapper::mapEntityToResponse)
                .toList();
        return AssistantMapper.mapToAssistantResponse(MINHA_BIBLIOTECA, isCache, listResponse);
    }

    public List<LivroResponse> getDocumentsByAutorMinhaBiblioteca(String autor) {
        var regexPattern = getRegexPattern(autor);
        log.info("Regex pattern: {}", regexPattern);
        return bookWiseRepository.buscarLivrosPearsonBibliotecaPorTermo(autor, MinhaBibliotecaBookEntity.class)
                .stream()
                .filter(livro -> Objects.nonNull(livro.getAutor()))
                .filter(livro -> regexPattern.matcher(livro.getAutor()).find())
                .map(MinhaBibliotecaMapper::mapEntityToResponse)
                .toList();
    }

    public List<LivroResponse> getCachedDocuments(String sessionId) {
        var fisicaKey = cacheService.getFisicaCacheKey(sessionId);
        var mbKey = cacheService.getMBCacheKey(sessionId);
        var pearsonKey = cacheService.getPearsonCacheKey(sessionId);

        var fisicaBooks = bookWiseRepository.getCachedFisica(fisicaKey);
        var pearsonBooks = bookWiseRepository.getCachedPearson(pearsonKey);
        var mbBooks = bookWiseRepository.getCachedMb(mbKey);

        var resultList = new ArrayList<LivroResponse>();
        if (Objects.nonNull(fisicaBooks)) {
            resultList.addAll(fisicaBooks.getListFisica()
                    .stream()
                    .skip(5)
                    .filter(Objects::nonNull)
                    .map(FisicaMapper::mapEntityToResponse)
                    .toList());
            bookWiseRepository.deleteObject(fisicaBooks, "cache-fisica");
        }
        if (Objects.nonNull(pearsonBooks)) {
            resultList.addAll(pearsonBooks.getListPearsonBook()
                    .stream()
                    .skip(5)
                    .filter(Objects::nonNull)
                    .map(PearsonMapper::mapEntityToResponse)
                    .toList());
            bookWiseRepository.deleteObject(pearsonBooks, "cache-pearson");
        }
        if (Objects.nonNull(mbBooks)) {
            resultList.addAll(mbBooks.getListMBBook()
                    .stream()
                    .skip(5)
                    .filter(Objects::nonNull)
                    .map(MinhaBibliotecaMapper::mapEntityToResponse)
                    .toList());
            bookWiseRepository.deleteObject(mbBooks, "cache-minha-biblioteca");
        }

        return resultList;
    }

    private Pattern getRegexPattern(String term) {
        List<String> splittedTerm = Arrays.stream(term.split(" ")).toList();
        var regex = ".*?" + String.join(".*?", splittedTerm);
        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    private boolean validateBook(String searchTerm, String titulo) {
        var termosLimpos = removerStopWords(removerAcentos(searchTerm));
        var tituloLimpo = removerStopWords(removerAcentos(titulo));
        if (tituloLimpo.isEmpty()) {
            return false;
        }
        for (String palavra : termosLimpos) {
            if (tituloLimpo.contains(palavra)) {
                return true;
            }
        }
        return false;
    }

    private List<String> removerStopWords(String termoDeBusca) {
        List<String> palavrasDoTermo = new ArrayList<>(Arrays.asList(termoDeBusca.toLowerCase().split("\\s+")));
        palavrasDoTermo.removeAll(stopWords);
        return palavrasDoTermo;
    }

    private static String removerAcentos(String str) {
        return Normalizer.normalize(str, Normalizer.Form.NFD)
                .replaceAll("[\\p{InCombiningDiacriticalMarks}]", "");
    }

    private List<FisicaBookEntity> excludeEquals(List<FisicaBookEntity> livros) {
        List<FisicaBookEntity> refinedList = new ArrayList<>();
        if (Objects.isNull(livros) || livros.isEmpty()) {
            return Collections.emptyList();
        }
        Set<String> seen = new HashSet<>();
        for (var it : livros) {
            if (!seen.contains(it.getNumeroDeChamada())) {
                refinedList.add(it);
                seen.add(it.getNumeroDeChamada());
            }
        }
        return refinedList;
    }

}
