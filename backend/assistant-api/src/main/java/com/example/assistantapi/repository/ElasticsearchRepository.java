package com.example.assistantapi.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.assistantapi.entity.ElasticBookEntity;
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
    private final testRepository testRepository;

    private static final String INDEX_NAME = "minha-biblioteca";

    public ElasticBookEntity getDocumentById(String bookId) throws IOException {
        ElasticBookEntity bookEntity = null;
        GetResponse<ElasticBookEntity> response = elasticsearchClient.get(g -> g
                .index(INDEX_NAME)
                .id(bookId), ElasticBookEntity.class);

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

    public ElasticBookEntity getOneDocumentByAutor(String value) {
        var searchBuilder = new SearchRequest.Builder();
        var request = searchBuilder.query(
                QueryBuilders.matchPhrase(m ->
                        m.field("autor").query(value)
                )).index(INDEX_NAME).build();

        var test = testRepository.findByAutor(value);
        return test;

//        var searchResponse = makeClientRequest(request);
//
//        List<Hit<ElasticBookEntity>> hits = searchResponse.hits().hits();
//        List<ElasticBookEntity> books = new ArrayList<>();
//
//        for (Hit<ElasticBookEntity> object : hits) {
//            log.info("{}", object.source());
//
//            books.add(object.source());
//        }
//
//        return books;
    }

    public ElasticBookEntity getDocumentByTitulo(String value) {
        return testRepository.findByTitulo(value);
    }

    public List<String> getDocumentsByTitulo(String value) {
        var test = testRepository.findAllByTitulo(value);
        List<String> books = new ArrayList<>();
        for (var t : test) {
            books.add(t.getTitulo());
        }
        return books;

//        var searchBuilder = new SearchRequest.Builder();
//        var request = searchBuilder.query(
//                QueryBuilders.match(m ->
//                        m.field("titulo").query(value)))
//                .index(INDEX_NAME).build();
//
//        log.info("Request: {}", request);
//
//        var searchResponse = makeClientRequest(request);
//
//        List<Hit<ElasticBookEntity>> hits = searchResponse.hits().hits();
//        List<String> books = new ArrayList<>();
//
//        log.info("Hits da response: {}", hits);
//
//        for (Hit<ElasticBookEntity> object : hits) {
//            log.info("{}", object.source());
//
//            if (object.source() != null) {
//                books.add(object.source().getTitulo());
//            } else {
//                books.add("nenhum livro encontrado.");
//            }
//        }
//
//        return books;
    }

    public List<String> getDocumentsByAutor(String value) {
        var searchBuilder = new SearchRequest.Builder();
        var request = searchBuilder.query(
                        QueryBuilders.match(m ->
                                m.field("autor").query(value)))
                .index(INDEX_NAME).build();

        var searchResponse = makeClientRequest(request);

        List<Hit<ElasticBookEntity>> hits = searchResponse.hits().hits();
        List<String> books = new ArrayList<>();

        for (Hit<ElasticBookEntity> object : hits) {
            log.info("{}", object.source());

            assert object.source() != null;
            books.add(object.source().getTitulo());
        }

        return books;
    }

    private SearchResponse<ElasticBookEntity> makeClientRequest(SearchRequest request) {
        try {
            return elasticsearchClient.search(request, ElasticBookEntity.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
