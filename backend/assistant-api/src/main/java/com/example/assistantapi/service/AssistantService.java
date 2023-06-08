package com.example.assistantapi.service;

import com.example.assistantapi.entity.BookEntity;
import com.example.assistantapi.entity.ElasticBookEntity;
import com.example.assistantapi.repository.BookRepository;
import com.example.assistantapi.repository.ElasticsearchRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@AllArgsConstructor
public class AssistantService {

    private BookRepository bookRepository;
    private ElasticsearchRepository elasticsearchRepository;

    public List<BookEntity> getBooksList() {
        return bookRepository.findAll();
    }

    public List<String> getBooksNames() {
        return bookRepository.findAllBooksNames();
    }

    public List<BookEntity> getBooksByAuthor(String author) {
        return bookRepository.findAllByAuthor(author);
    }

    public List<ElasticBookEntity> getAllElasticBooks() {
        try {
            return elasticsearchRepository.searchAllDocuments();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
