package com.example.assistantapi.controller;

import com.example.assistantapi.entity.MinhaBibliotecaResponse;
import com.example.assistantapi.service.AssistantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/assistant")
@AllArgsConstructor
public class AssistantController {

    private final AssistantService assistantService;

    @GetMapping(value = "/books/minha-biblioteca/titulo/{titulo}")
    @ResponseStatus(HttpStatus.OK)
    public List<MinhaBibliotecaResponse> searchDocumentsByTitulo(@PathVariable String titulo) {
        return assistantService.getDocumentsByTituloMinhaBiblioteca(titulo);
    }

    @GetMapping(value = "/books/minha-biblioteca/autor/{autor}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> searchDocumentsByAutorMinha(@PathVariable String autor) {
        log.info("Autor a pesquisar: {}", autor);
        return assistantService.getDocumentsByAutorMinhaBiblioteca(autor);
    }

    @GetMapping(value = "/books/pearson-biblioteca/titulo/{titulo}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> searchDocumentsByTituloPearson(@PathVariable String titulo) {
        return assistantService.getDocumentsByTituloPearsonBiblioteca(titulo);
    }

    @GetMapping(value = "/books/pearson-biblioteca/autor/{autor}")
    @ResponseStatus(HttpStatus.OK)
    public List<String> searchDocumentsByAutorPearson(@PathVariable String autor) {
        return assistantService.getDocumentsByAutorPearsonBiblioteca(autor);
    }

}
