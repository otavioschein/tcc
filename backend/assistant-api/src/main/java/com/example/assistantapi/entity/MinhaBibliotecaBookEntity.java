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
@Document(indexName = "minha-biblioteca")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MinhaBibliotecaBookEntity {

    @Id
    private String id;
    private String titulo;
    private String autor;
    private String isbnDigital;
    private String isbnImpresso;
    private String referenciaAbnt;
    private String editora;
    private String formato;
    private String cursos;
    private String seloEditorial;
    private String edicao;

}
