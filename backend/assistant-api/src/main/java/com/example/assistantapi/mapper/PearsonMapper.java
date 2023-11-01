package com.example.assistantapi.mapper;

import com.example.assistantapi.entity.PearsonBibliotecaBookEntity;
import com.example.assistantapi.response.LivroResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class PearsonMapper {

    private final String PEARSON_URL = "https://plataforma.bvirtual.com.br/Acervo/Publicacao/";

    public LivroResponse mapEntityToResponse(PearsonBibliotecaBookEntity entity) {
        return LivroResponse.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .autor(entity.getAutor())
                .acesso(PEARSON_URL + entity.getId())
                .build();
    }

}
