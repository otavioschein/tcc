package com.example.assistantapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.opencsv.bean.CsvBindByName;
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
@Document(collection = "minha-biblioteca")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MinhaBibliotecaBookEntity {

    @Id
    private String id;
    @TextIndexed
    @CsvBindByName(column = "titulo")
    private String titulo;
    @TextIndexed
    @CsvBindByName(column = "autor")
    private String autor;
    @CsvBindByName(column = "isbn_digital")
    private String isbnDigital;
    @CsvBindByName(column = "isbn_impresso")
    private String isbnImpresso;
    @CsvBindByName(column = "referencia_abnt")
    private String referenciaAbnt;
    @CsvBindByName(column = "editora")
    private String editora;
    @CsvBindByName(column = "formato")
    private String formato;
    @CsvBindByName(column = "cursos")
    private String cursos;
    @CsvBindByName(column = "selo_editorial")
    private String seloEditorial;
    @CsvBindByName(column = "edicao")
    private String edicao;

}
