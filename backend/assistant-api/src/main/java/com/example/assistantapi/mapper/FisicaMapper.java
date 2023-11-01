package com.example.assistantapi.mapper;

import com.example.assistantapi.entity.FisicaBookEntity;
import com.example.assistantapi.response.LivroResponse;
import lombok.experimental.UtilityClass;

@UtilityClass
public class FisicaMapper {

    public LivroResponse mapEntityToResponse(FisicaBookEntity entity) {
        return LivroResponse.builder()
                .id(entity.get_id())
                .titulo(entity.getTitulo())
                .autor(entity.getAutor())
                .acesso(entity.getNumeroDeChamada())
                .build();
    }

}
