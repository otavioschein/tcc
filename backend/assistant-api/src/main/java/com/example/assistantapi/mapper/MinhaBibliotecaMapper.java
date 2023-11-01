package com.example.assistantapi.mapper;

import com.example.assistantapi.entity.MinhaBibliotecaBookEntity;
import com.example.assistantapi.response.LivroResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MinhaBibliotecaMapper {

    private final String URL_ACESSO = "https://integrada.minhabiblioteca.com.br/#/books/";

    public LivroResponse mapEntityToResponse(MinhaBibliotecaBookEntity mbEntity) {
        return LivroResponse.builder()
                .id(mbEntity.getId())
                .titulo(mbEntity.getTitulo())
                .autor(mbEntity.getAutor())
                .acesso(URL_ACESSO + mbEntity.getIsbnDigital())
                .build();
    }

}
