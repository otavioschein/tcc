package com.example.assistantapi.repository;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
import co.elastic.clients.elasticsearch.core.GetResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.example.assistantapi.entity.FisicaBookEntity;
import com.example.assistantapi.entity.MinhaBibliotecaBookEntity;
import com.example.assistantapi.entity.PearsonBibliotecaBookEntity;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.util.List;

@Slf4j
@Repository
@AllArgsConstructor
public class ElasticsearchRepository {

    private final ElasticsearchClient elasticsearchClient;

    private static final String INDEX_MINHA_BIBLIOTECA = "minha-biblioteca";
    private static final String INDEX_PEARSON_BIBLIOTECA = "pearson-biblioteca";
    private static final String INDEX_BIBLIOTECA_FISICA = "fisica-biblioteca";

    public List<Hit<MinhaBibliotecaBookEntity>> minhaBibliotecaGetDocumentsByTitulo(String value) {
        var searchBuilder = new SearchRequest.Builder();

        var requestMatchPhrase = searchBuilder.query(
                QueryBuilders.matchPhrase(mp ->
                        mp.field("titulo").query(value))
        ).index(INDEX_MINHA_BIBLIOTECA).build();

        var searchResponse = makeClientRequest(requestMatchPhrase, MinhaBibliotecaBookEntity.class);

        return searchResponse.hits().hits();
    }

    public List<Hit<MinhaBibliotecaBookEntity>> minhaBibliotecaGetSimilarDocumentsByTitulo(String value) {
        var searchBuilder = new SearchRequest.Builder();
        var requestMatch = searchBuilder.query(
                        QueryBuilders.match(m ->
                                m.field("titulo").query(value)))
                .index(INDEX_MINHA_BIBLIOTECA).build();
        var otherResponse = makeClientRequest(requestMatch, MinhaBibliotecaBookEntity.class);
        log.info("2 hits: {}", otherResponse.hits().hits());
        return otherResponse.hits().hits();
    }

    public List<Hit<MinhaBibliotecaBookEntity>> minhaBibliotecaGetDocumentsByAutor(String value) {
        var searchBuilder = new SearchRequest.Builder();
        var requestMatchPhrase = searchBuilder.query(
                        QueryBuilders.matchPhrase(m ->
                                m.field("autor").query(value)))
                .index(INDEX_MINHA_BIBLIOTECA).build();

        var searchResponse = makeClientRequest(requestMatchPhrase, MinhaBibliotecaBookEntity.class);

        return searchResponse.hits().hits();
    }

    public List<Hit<MinhaBibliotecaBookEntity>> minhaBibliotecaGetSimilarDocumentsByAutor(String value) {
        var searchBuilder = new SearchRequest.Builder();
        var requestMatch = searchBuilder.query(
                QueryBuilders.match(m ->
                        m.field("autor").query(value))
        ).index(INDEX_MINHA_BIBLIOTECA).build();
        var response = makeClientRequest(requestMatch, MinhaBibliotecaBookEntity.class);
        return response.hits().hits();
    }

    public List<Hit<PearsonBibliotecaBookEntity>> pearsonGetDocumentsByTitulo(String value) {
        var searchBuilder = new SearchRequest.Builder();
        var requestPhrase = searchBuilder.query(
                        QueryBuilders.matchPhrase(m ->
                                m.field("titulo").query(value)))
                .index(INDEX_PEARSON_BIBLIOTECA).build();

        log.info("Request: {}", requestPhrase);

        var searchResponse = makeClientRequest(requestPhrase, PearsonBibliotecaBookEntity.class);

        return searchResponse.hits().hits();
    }

    public List<Hit<PearsonBibliotecaBookEntity>> pearsonGetSimilarDocumentsByTitulo(String value) {
        var searchBuilder = new SearchRequest.Builder();
        var requestMatch = searchBuilder.query(
                QueryBuilders.match(m ->
                        m.field("titulo").query(value))
        ).index(INDEX_PEARSON_BIBLIOTECA).build();
        var response = makeClientRequest(requestMatch, PearsonBibliotecaBookEntity.class);
        return response.hits().hits();
    }

    public List<Hit<PearsonBibliotecaBookEntity>> pearsonGetDocumentsByAutor(String value) {
        var searchBuilder = new SearchRequest.Builder();
        var requestPhrase = searchBuilder.query(
                        QueryBuilders.matchPhrase(m ->
                                m.field("autor").query(value)))
                .index(INDEX_PEARSON_BIBLIOTECA).build();

        var searchResponse = makeClientRequest(requestPhrase, PearsonBibliotecaBookEntity.class);
        return searchResponse.hits().hits();
    }

    public List<Hit<PearsonBibliotecaBookEntity>> pearsonGetSimilarDocumentsByAutor(String value) {
        var searchBuilder = new SearchRequest.Builder();
        var requestMatch = searchBuilder.query(
                QueryBuilders.match(m ->
                        m.field("autor").query(value))
        ).index(INDEX_PEARSON_BIBLIOTECA).build();

        var response = makeClientRequest(requestMatch, PearsonBibliotecaBookEntity.class);
        return response.hits().hits();
    }

    //TODO
    public List<Hit<FisicaBookEntity>> fisicaGetDocumentsByTitulo(String titulo) {
        var searchBuilder = new SearchRequest.Builder();
        var requestPhrase = searchBuilder.query(
                        QueryBuilders.matchPhrase(m ->
                                m.field("titulo").query(titulo)))
                .index(INDEX_BIBLIOTECA_FISICA).build();

        var searchResponse = makeClientRequest(requestPhrase, FisicaBookEntity.class);
        return searchResponse.hits().hits();
    }

    public List<Hit<FisicaBookEntity>> fisicaGetSimilarDocumentsByTitulo(String titulo) {
        var searchBuilder = new SearchRequest.Builder();
        var requestPhrase = searchBuilder.query(
                        QueryBuilders.match(m ->
                                m.field("titulo").query(titulo)))
                .index(INDEX_BIBLIOTECA_FISICA).build();

        var searchResponse = makeClientRequest(requestPhrase, FisicaBookEntity.class);
        return searchResponse.hits().hits();
    }

    public List<Hit<FisicaBookEntity>> fisicaGetDocumentsByAutor(String autor) {
        var searchBuilder = new SearchRequest.Builder();
        var requestPhrase = searchBuilder.query(
                        QueryBuilders.matchPhrase(m ->
                                m.field("autor").query(autor)))
                .index(INDEX_BIBLIOTECA_FISICA).build();

        var searchResponse = makeClientRequest(requestPhrase, FisicaBookEntity.class);
        return searchResponse.hits().hits();
    }

    public List<Hit<FisicaBookEntity>> fisicaGetSimilarDocumentsByAutor(String autor) {
        var searchBuilder = new SearchRequest.Builder();
        var requestPhrase = searchBuilder.query(
                        QueryBuilders.match(m ->
                                m.field("autor").query(autor)))
                .index(INDEX_BIBLIOTECA_FISICA).build();

        var searchResponse = makeClientRequest(requestPhrase, FisicaBookEntity.class);
        return searchResponse.hits().hits();
    }

    private <T> SearchResponse<T> makeClientRequest(SearchRequest request, Class<T> classType) {
        try {
            return elasticsearchClient.search(request, classType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
