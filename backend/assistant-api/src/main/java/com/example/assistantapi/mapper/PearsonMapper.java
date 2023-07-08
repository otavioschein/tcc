package com.example.assistantapi.mapper;

import com.example.assistantapi.entity.PearsonBibliotecaBookEntity;
import com.example.assistantapi.response.PearsonResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PearsonMapper {

    private final String PEARSON_URL = "https://plataforma.bvirtual.com.br/Acervo/Publicacao/";
    private final String PEARSON_BIBLIOTECA = "Pearson Biblioteca";

    public PearsonResponse mapEntityToResponse(PearsonBibliotecaBookEntity entity) {
        return PearsonResponse.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .autor(entity.getAutor())
                .biblioteca(PEARSON_BIBLIOTECA)
                .urlAcesso(PEARSON_URL + entity.getId())
                .build();
    }

}
