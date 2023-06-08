package com.example.assistantapi.controller;

import com.example.assistantapi.entity.BookEntity;
import com.example.assistantapi.entity.ElasticBookEntity;
import com.example.assistantapi.repository.ElasticsearchRepository;
import com.example.assistantapi.service.AssistantService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/assistant")
@AllArgsConstructor
public class AssistantController {

    private final AssistantService assistantService;
    private final ElasticsearchRepository elasticRepository;

    @GetMapping(value = "/search/books")
    public List<BookEntity> getBooks() {
        return assistantService.getBooksList();
    }

    @GetMapping(value = "/search/books/names")
    public List<String> getBooksNames() {
        return assistantService.getBooksNames();
    }

    @GetMapping(value = "/search/books/{author}")
    public List<BookEntity> getBooksByAuthorName(@PathVariable String author) {
        return assistantService.getBooksByAuthor(author);
    }

    @GetMapping(value = "/elastic/books/{id}")
    public ElasticBookEntity searchDocumentById(@PathVariable String id) throws IOException {
        return elasticRepository.getDocumentById(id);
    }

    @GetMapping(value = "/elastic/books")
    public List<ElasticBookEntity> searchAllDocuments() {
        return assistantService.getAllElasticBooks();
    }

}
