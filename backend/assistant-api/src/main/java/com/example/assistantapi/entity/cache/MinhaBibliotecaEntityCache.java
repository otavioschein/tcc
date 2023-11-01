package com.example.assistantapi.entity.cache;

import com.example.assistantapi.entity.MinhaBibliotecaBookEntity;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "cache-minha-biblioteca")
@JsonIgnoreProperties(ignoreUnknown = true)
public class MinhaBibliotecaEntityCache {

    @Id
    private String cacheId;
    private List<MinhaBibliotecaBookEntity> listMBBook;

}
