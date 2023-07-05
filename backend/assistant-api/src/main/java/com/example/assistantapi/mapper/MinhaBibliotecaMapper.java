package com.example.assistantapi.mapper;

import com.example.assistantapi.entity.MinhaBibliotecaBookEntity;
import com.example.assistantapi.entity.MinhaBibliotecaResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MinhaBibliotecaMapper {

    private final String MINHA_BIBLIOTECA = "Minha Biblioteca";
    private final String URL_ACESSO = "https://integrada.minhabiblioteca.com.br/#/books/";

    public MinhaBibliotecaResponse mapEntityToResponse(MinhaBibliotecaBookEntity mbEntity) {
        return MinhaBibliotecaResponse.builder()
                .titulo(mbEntity.getTitulo())
                .autor(mbEntity.getAutor())
                .biblioteca(MINHA_BIBLIOTECA)
                .urlAcesso(URL_ACESSO + mbEntity.getIsbnDigital())
                .build();
    }

}
