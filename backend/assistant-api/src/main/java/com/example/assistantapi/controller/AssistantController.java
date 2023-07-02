package com.example.assistantapi.controller;

import com.example.assistantapi.entity.ElasticBookEntity;
import com.example.assistantapi.service.AssistantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/assistant")
@AllArgsConstructor
public class AssistantController {

    private final AssistantService assistantService;

    @GetMapping(value = "/books/titulo")
    public List<String> searchDocumentsByTitulo(@RequestParam String titulo) {
        log.info("Titulo a pesquisar: {}", titulo);
        return assistantService.getDocumentsByTitulo(titulo);
    }

    @GetMapping(value = "/books/one/titulo")
    public ElasticBookEntity searchOneTitulo(@RequestParam String titulo) {
        return assistantService.getDocumentByTitulo(titulo);
    }

    @GetMapping(value = "/books/autor")
    public List<String> searchDocumentsByAturo(@RequestParam String autor) {
        log.info("Autor a pesquisar: {}", autor);
        return assistantService.getDocumentsByAutor(autor);
    }

    @GetMapping(value = "/books/one/autor")
    public ElasticBookEntity searchDocumentsByAutor(@RequestParam String autor) {
        log.info("Autor a pesquisar: {}", autor);
        return assistantService.getDocumentByAutor(autor);
    }

}
