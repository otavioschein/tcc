package com.example.assistantapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(indexName = "books")
@JsonIgnoreProperties(ignoreUnknown = true)
public class ElasticBookEntity {

    @Id
    @Field(type = FieldType.Keyword, name = "_id")
    private String id;

    @Field(type = FieldType.Long, name = "bookID")
    private String bookID;

    @Field(type = FieldType.Text, name = "title")
    private String title;

    @Field(type = FieldType.Text, name = "authors")
    private String authors;

//    @Field(type = FieldType.Double, name = "publicationDate")
//    private double price;

}
