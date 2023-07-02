package com.example.assistantapi.repository;

import com.example.assistantapi.entity.ElasticBookEntity;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface testRepository extends ElasticsearchRepository<ElasticBookEntity, String> {

    List<ElasticBookEntity> findAllByAutor(String autor);

    ElasticBookEntity findByAutor(String autor);

    List<ElasticBookEntity> findAllByTitulo(String titulo);

    ElasticBookEntity findByTitulo(String titulo);

}
