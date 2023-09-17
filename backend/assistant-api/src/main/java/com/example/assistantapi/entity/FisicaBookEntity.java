package com.example.assistantapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "fisica-biblioteca")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FisicaBookEntity {

    @Id
    private String id;
    private String titulo;
    private String autor;

}
