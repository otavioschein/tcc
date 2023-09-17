package com.example.assistantapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.TextIndexed;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "pearson-biblioteca")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PearsonBibliotecaBookEntity {

    @Id
    private String id;
    @TextIndexed
    private String titulo;
    @TextIndexed
    private String autor;
    private String anoDaPublicacao;
    private String categoria;
    private String dataDaPublicacao;
    private String descricao;
    private String edicao;
    private String editora;
    private String formato;
    private String idioma;
    private String isbn;
    private String paginas;
    private String selo;
    private String status;
    private String tags;

}
