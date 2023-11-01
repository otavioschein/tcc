package com.example.assistantapi.controller;

import com.example.assistantapi.response.AssistantResponse;
import com.example.assistantapi.response.LivroResponse;
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

    @GetMapping(value = "{sessionId}/books/minha-biblioteca/titulo/{titulo}")
    @ResponseStatus(HttpStatus.OK)
    public AssistantResponse searchDocumentsByTitulo(@PathVariable String sessionId, @PathVariable String titulo) {
        log.info("Minha biblioteca, titulo: {}", titulo);
        return assistantService.getDocumentsByTituloMinhaBiblioteca(sessionId, titulo);
    }

    @GetMapping(value = "/books/minha-biblioteca/autor/{autor}")
    @ResponseStatus(HttpStatus.OK)
    public List<LivroResponse> searchDocumentsByAutorMinhaBiblioteca(@PathVariable String autor) {
        log.info("Minha biblioteca, autor: {}", autor);
        return assistantService.getDocumentsByAutorMinhaBiblioteca(autor);
    }

    @GetMapping(value = "{sessionId}/books/pearson-biblioteca/titulo/{titulo}")
    @ResponseStatus(HttpStatus.OK)
    public AssistantResponse searchDocumentsByTituloPearson(@PathVariable String sessionId, @PathVariable String titulo) {
        log.info("Biblioteca pearson, titulo: {}", titulo);
        return assistantService.getDocumentsByTituloPearsonBiblioteca(sessionId, titulo);
    }


    @GetMapping(value = "/books/pearson-biblioteca/autor/{autor}")
    @ResponseStatus(HttpStatus.OK)
    public List<LivroResponse> searchDocumentsByAutorPearson(@PathVariable String autor) {
        log.info("Biblioteca pearson, autor: {}", autor);
        return assistantService.getDocumentsByAutorPearsonBiblioteca(autor);
    }

    @GetMapping(value = "{sessionId}/books/biblioteca-fisica/titulo/{titulo}")
    @ResponseStatus(HttpStatus.OK)
    public AssistantResponse searchDocumentsByTituloFisica(@PathVariable String sessionId, @PathVariable String titulo) {
        log.info("Biblioteca física, titulo: {}", titulo);
        return assistantService.getDocumentsByTituloBibliotecaFisica(sessionId, titulo);
    }

    @GetMapping(value = "/books/biblioteca-fisica/autor/{autor}")
    @ResponseStatus(HttpStatus.OK)
    public List<LivroResponse> searchDocumentsByAutorFisica(@PathVariable String autor) {
        log.info("Biblioteca física, autor: {}", autor);
        return assistantService.getDocumentsByAutorBibliotecaFisica(autor);
    }

    @GetMapping(value = "{sessionId}/books/mais-opcoes")
    @ResponseStatus(HttpStatus.OK)
    public List<LivroResponse> getCachedPearsonBooks(@PathVariable String sessionId) {
        log.info("Buscando mais opções para a sessão: {}", sessionId);
        return assistantService.getCachedDocuments(sessionId);
    }

}
