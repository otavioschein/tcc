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
@Document(collection = "fisica-biblioteca")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FisicaBookEntity {

    @Id
    private String _id;
    @TextIndexed
    @CsvBindByName(column = "titulo")
    private String titulo;
    @TextIndexed
    @CsvBindByName(column = "autor")
    private String autor;
    @CsvBindByName(column = "numero_de_chamada")
    private String numeroDeChamada;
    @CsvBindByName(column = "numero_de_controler")
    private String numeroDeControle;
    @CsvBindByName(column = "numero_do_tombo")
    private String numeroDoTombo;
    @CsvBindByName(column = "estado")
    private String estado;
    @CsvBindByName(column = "tipo_de_material")
    private String tipoDeMaterial;
    @CsvBindByName(column = "numero_da_obra")
    private String numeroDaObra;
    @CsvBindByName(column = "data_de_entrada")
    private String dataDeEntrada;
    @CsvBindByName(column = "volume")
    private String volume;
    @CsvBindByName(column = "edicao")
    private String edicao;
    @CsvBindByName(column = "lugar_pub")
    private String lugarPub;
    @CsvBindByName(column = "editora")
    private String editora;
    @CsvBindByName(column = "data_pub")
    private String dataPub;

}
