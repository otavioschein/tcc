package com.example.assistantapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "pearson-biblioteca")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PearsonBibliotecaBookEntity {

    @Id
    private String id;
    private String anoDaPublicacao;
    private String autor;
    private String categaria;
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
    private String titulo;

}
