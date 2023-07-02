package com.example.assistantapi.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.assistantapi.entity.MinhaBibliotecaBook;
import com.example.assistantapi.entity.PearsonBibliotecaBook;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class ElasticsearchRepository {

    private final ElasticsearchClient elasticsearchClient;

    private static final String INDEX_MINHA_BIBLIOTECA = "minha-biblioteca";
    private static final String INDEX_PEARSON_BIBLIOTECA = "pearson-biblioteca";

    public MinhaBibliotecaBook minhaBibliotecaGetDocumentById(String bookId) throws IOException {
        MinhaBibliotecaBook bookEntity = null;
        GetResponse<MinhaBibliotecaBook> response = elasticsearchClient.get(g -> g
                .index(INDEX_MINHA_BIBLIOTECA)
                .id(bookId), MinhaBibliotecaBook.class);

        if (response.found()) {
            bookEntity = response.source();
            log.info("Response: {}", response);
            log.info("Response source: {}", response.source());
            log.info("Book name {}", bookEntity.getTitulo());
        } else {
            log.info("Book not found.");
        }

        return bookEntity;
    }

    public List<String> minhaBibliotecaGetDocumentsByTitulo(String value) {
        var searchBuilder = new SearchRequest.Builder();
        var request = searchBuilder.query(
                        QueryBuilders.match(m ->
                                m.field("titulo").query(value)))
                .index(INDEX_MINHA_BIBLIOTECA).build();

        log.info("Request: {}", request);

        var searchResponse = makeClientRequest(request, MinhaBibliotecaBook.class);

        List<Hit<MinhaBibliotecaBook>> hits = searchResponse.hits().hits();
        List<String> books = new ArrayList<>();

        log.info("Hits da response: {}", hits);

        for (Hit<MinhaBibliotecaBook> object : hits) {
            log.info("{}", object.source());

            if (object.source() != null) {
                books.add(object.source().getTitulo());
            } else {
                books.add("nenhum livro encontrado.");
            }
        }

        return books;
    }

    public List<String> minhaBibliotecaGetDocumentsByAutor(String value) {
        var searchBuilder = new SearchRequest.Builder();
        var request = searchBuilder.query(
                        QueryBuilders.match(m ->
                                m.field("autor").query(value)))
                .index(INDEX_MINHA_BIBLIOTECA).build();

        var searchResponse = makeClientRequest(request, MinhaBibliotecaBook.class);

        List<Hit<MinhaBibliotecaBook>> hits = searchResponse.hits().hits();
        List<String> books = new ArrayList<>();

        for (Hit<MinhaBibliotecaBook> object : hits) {
            log.info("{}", object.source());

            assert object.source() != null;
            books.add(object.source().getTitulo());
        }

        return books;
    }

    public List<String> pearsonGetDocumentsByTitulo(String value) {
        var searchBuilder = new SearchRequest.Builder();
        var request = searchBuilder.query(
                        QueryBuilders.match(m ->
                                m.field("titulo").query(value)))
                .index(INDEX_PEARSON_BIBLIOTECA).build();

        log.info("Request: {}", request);

        var searchResponse = makeClientRequest(request, PearsonBibliotecaBook.class);

        List<Hit<PearsonBibliotecaBook>> hits = searchResponse.hits().hits();
        List<String> books = new ArrayList<>();

        log.info("Hits da response: {}", hits);

        for (Hit<PearsonBibliotecaBook> object : hits) {
            log.info("{}", object.source());

            if (object.source() != null) {
                books.add(object.source().getTitulo());
            } else {
                books.add("nenhum livro encontrado.");
            }
        }

        return books;
    }

    public List<String> pearsonGetDocumentsByAutor(String value) {
        var searchBuilder = new SearchRequest.Builder();
        var request = searchBuilder.query(
                        QueryBuilders.match(m ->
                                m.field("autor").query(value)))
                .index(INDEX_PEARSON_BIBLIOTECA).build();

        var searchResponse = makeClientRequest(request, PearsonBibliotecaBook.class);

        List<Hit<PearsonBibliotecaBook>> hits = searchResponse.hits().hits();
        List<String> books = new ArrayList<>();

        for (Hit<PearsonBibliotecaBook> object : hits) {
            log.info("{}", object.source());

            assert object.source() != null;
            books.add(object.source().getTitulo());
        }

        return books;
    }

    private <T> SearchResponse<T> makeClientRequest(SearchRequest request, Class<T> classType) {
        try {
            return elasticsearchClient.search(request, classType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
