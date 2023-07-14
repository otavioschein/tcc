package com.example.assistantapi.controller;

import com.example.assistantapi.response.MinhaBibliotecaResponse;
import com.example.assistantapi.response.PearsonResponse;
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
        log.info("titulo: {}", titulo);
        return assistantService.getDocumentsByTituloMinhaBiblioteca(titulo);
    }

    @GetMapping(value = "/books/minha-biblioteca/titulo/semelhante/{titulo}")
    @ResponseStatus(HttpStatus.OK)
    public List<MinhaBibliotecaResponse> searchSimilarDocumentsByTitulo(@PathVariable String titulo) {
        log.info("titulo: {}", titulo);
        return assistantService.getSimilarDocumentsByTituloMinhaBiblioteca(titulo);
    }

    @GetMapping(value = "/books/minha-biblioteca/autor/{autor}")
    @ResponseStatus(HttpStatus.OK)
    public List<MinhaBibliotecaResponse> searchDocumentsByAutorMinhaBiblioteca(@PathVariable String autor) {
        log.info("Autor a pesquisar: {}", autor);
        return assistantService.getDocumentsByAutorMinhaBiblioteca(autor);
    }

    @GetMapping(value = "/books/minha-biblioteca/autor/semelhante/{autor}")
    @ResponseStatus(HttpStatus.OK)
    public List<MinhaBibliotecaResponse> searchSimilarDocumentsByAutorMinhaBiblioteca(@PathVariable String autor) {
        log.info("Autor a pesquisar: {}", autor);
        return assistantService.getSimilarDocumentsByAutorMinhaBiblioteca(autor);
    }

    @GetMapping(value = "/books/pearson-biblioteca/titulo/{titulo}")
    @ResponseStatus(HttpStatus.OK)
    public List<PearsonResponse> searchDocumentsByTituloPearson(@PathVariable String titulo) {
        log.info("titulo: {}", titulo);
        return assistantService.getDocumentsByTituloPearsonBiblioteca(titulo);
    }

    @GetMapping(value = "/books/pearson-biblioteca/titulo/semelhante/{titulo}")
    @ResponseStatus(HttpStatus.OK)
    public List<PearsonResponse> searchSimilarDocumentsByTituloPearson(@PathVariable String titulo) {
        log.info("titulo: {}", titulo);
        return assistantService.getSimilarDocumentsByTituloPearsonBiblioteca(titulo);
    }

    @GetMapping(value = "/books/pearson-biblioteca/autor/{autor}")
    @ResponseStatus(HttpStatus.OK)
    public List<PearsonResponse> searchDocumentsByAutorPearson(@PathVariable String autor) {
        return assistantService.getDocumentsByAutorPearsonBiblioteca(autor);
    }

    @GetMapping(value = "/books/pearson-biblioteca/autor/semelhante/{autor}")
    @ResponseStatus(HttpStatus.OK)
    public List<PearsonResponse> searchSimilarDocumentsByAutorPearson(@PathVariable String autor) {
        return assistantService.getSimilarDocumentsByAutorPearsonBiblioteca(autor);
    }

}
