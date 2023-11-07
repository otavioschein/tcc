package com.example.assistantapi.controller;

import com.example.assistantapi.response.AssistantResponse;
import com.example.assistantapi.response.LivroResponse;
import com.example.assistantapi.service.AssistantService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/assistant")
@AllArgsConstructor
public class AssistantController {

    private final AssistantService assistantService;

    @GetMapping(value = "{sessionId}/books/minha-biblioteca/titulo/{title}")
    @ResponseStatus(HttpStatus.OK)
    public AssistantResponse searchDocumentsByTituloMinhaBiblioteca(@PathVariable String sessionId, @PathVariable String title) {
        log.info("Minha biblioteca, titulo: {}", title);
        return assistantService.getDocumentsByTituloMinhaBiblioteca(sessionId, title);
    }

    @GetMapping(value = "/books/minha-biblioteca/autor/{author}")
    @ResponseStatus(HttpStatus.OK)
    public List<LivroResponse> searchDocumentsByAutorMinhaBiblioteca(@PathVariable String author) {
        log.info("Minha biblioteca, autor: {}", author);
        return assistantService.getDocumentsByAutorMinhaBiblioteca(author);
    }

    @GetMapping(value = "{sessionId}/books/pearson-biblioteca/titulo/{title}")
    @ResponseStatus(HttpStatus.OK)
    public AssistantResponse searchDocumentsByTituloPearson(@PathVariable String sessionId, @PathVariable String title) {
        log.info("Biblioteca pearson, titulo: {}", title);
        return assistantService.getDocumentsByTituloPearsonBiblioteca(sessionId, title);
    }


    @GetMapping(value = "/books/pearson-biblioteca/autor/{author}")
    @ResponseStatus(HttpStatus.OK)
    public List<LivroResponse> searchDocumentsByAutorPearson(@PathVariable String author) {
        log.info("Biblioteca pearson, autor: {}", author);
        return assistantService.getDocumentsByAutorPearsonBiblioteca(author);
    }

    @GetMapping(value = "{sessionId}/books/biblioteca-fisica/titulo/{title}")
    @ResponseStatus(HttpStatus.OK)
    public AssistantResponse searchDocumentsByTituloFisica(@PathVariable String sessionId, @PathVariable String title) {
        log.info("Biblioteca física, titulo: {}", title);
        return assistantService.getDocumentsByTituloBibliotecaFisica(sessionId, title);
    }

    @GetMapping(value = "/books/biblioteca-fisica/autor/{author}")
    @ResponseStatus(HttpStatus.OK)
    public List<LivroResponse> searchDocumentsByAutorFisica(@PathVariable String author) {
        log.info("Biblioteca física, autor: {}", author);
        return assistantService.getDocumentsByAutorBibliotecaFisica(author);
    }

    @GetMapping(value = "{sessionId}/books/mais-opcoes")
    @ResponseStatus(HttpStatus.OK)
    public List<LivroResponse> getCachedBooks(@PathVariable String sessionId) {
        log.info("Buscando mais opções para a sessão: {}", sessionId);
        return assistantService.getCachedDocuments(sessionId);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/update/catalog")
    public ResponseEntity<String> updateCatalog(@RequestParam MultipartFile file, @RequestParam String library) throws Exception {
        return ResponseEntity.ok(assistantService.updateCatalog(file, library));
    }
}
