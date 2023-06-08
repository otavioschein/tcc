package com.example.assistantapi.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.assistantapi.entity.ElasticBookEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
public class ElasticsearchRepository {

    @Autowired
    private ElasticsearchClient elasticsearchClient;

    private static final String INDEX_NAME = "books";

    public ElasticBookEntity getDocumentById(String bookId) throws IOException {
        ElasticBookEntity bookEntity = null;
        GetResponse<ElasticBookEntity> response = elasticsearchClient.get(g -> g
                .index(INDEX_NAME)
                .id(bookId), ElasticBookEntity.class);

        if (response.found()) {
            bookEntity = response.source();
            bookEntity.setId(response.id());
            log.info("Book name {}", bookEntity.getTitle());
        } else {
            log.info("Book not found.");
        }

        return bookEntity;
    }

    public List<ElasticBookEntity> searchAllDocuments() throws IOException {
        var request = SearchRequest.of(s -> s
                .index(INDEX_NAME));

        var searchResponse = elasticsearchClient.search(request, ElasticBookEntity.class);

        List<Hit<ElasticBookEntity>> hits = searchResponse.hits().hits();
        List<ElasticBookEntity> books = new ArrayList<>();

        for (Hit<ElasticBookEntity> object : hits) {
            log.info("{}", object.source());
            books.add(object.source());
        }

        return books;
    }

}
