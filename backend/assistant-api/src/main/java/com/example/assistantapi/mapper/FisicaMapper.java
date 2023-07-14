package com.example.assistantapi.mapper;

import com.example.assistantapi.entity.FisicaBookEntity;
import com.example.assistantapi.response.FisicaResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FisicaMapper {

    private final String FISICA_BIBLIOTECA = "Biblioteca Física";

    public FisicaResponse mapEntityToResponse(FisicaBookEntity entity) {
        return FisicaResponse.builder()
                .id(entity.getId())
                .titulo(entity.getTitulo())
                .autor(entity.getAutor())
                .biblioteca(FISICA_BIBLIOTECA)
                .build();
    }

}
