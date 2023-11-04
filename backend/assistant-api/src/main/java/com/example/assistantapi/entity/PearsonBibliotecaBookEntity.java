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
@Document(collection = "pearson-biblioteca")
@JsonIgnoreProperties(ignoreUnknown = true)
public class PearsonBibliotecaBookEntity {

    @Id
    private String _id;
    @CsvBindByName(column = "id")
    private String id;
    @TextIndexed
    @CsvBindByName(column = "titulo")
    private String titulo;
    @TextIndexed
    @CsvBindByName(column = "autor")
    private String autor;
    @CsvBindByName(column = "ano_da_publicacao")
    private String anoDaPublicacao;
    @CsvBindByName(column = "categoria")
    private String categoria;
    @CsvBindByName(column = "data_da_publicacao")
    private String dataDaPublicacao;
    @CsvBindByName(column = "descricao")
    private String descricao;
    @CsvBindByName(column = "edicao")
    private String edicao;
    @CsvBindByName(column = "editora")
    private String editora;
    @CsvBindByName(column = "formato")
    private String formato;
    @CsvBindByName(column = "idioma")
    private String idioma;
    @CsvBindByName(column = "isbn")
    private String isbn;
    @CsvBindByName(column = "paginas")
    private String paginas;
    @CsvBindByName(column = "selo")
    private String selo;
    @CsvBindByName(column = "status")
    private String status;
    @CsvBindByName(column = "tags")
    private String tags;
}
