package com.example.assistantapi.entity.cache;

import com.example.assistantapi.entity.FisicaBookEntity;
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
@Document(collection = "cache-fisica")
@JsonIgnoreProperties(ignoreUnknown = true)
public class FisicaEntityCache {

    @Id
    private String cacheId;
    private List<FisicaBookEntity> listFisica;

}
