package com.example.assistantapi.mapper;

import com.example.assistantapi.response.AssistantResponse;
import com.example.assistantapi.response.LivroResponse;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class AssistantMapper {

    public AssistantResponse mapToAssistantResponse(String biblioteca, boolean isCache, List<LivroResponse> livroList) {
        return AssistantResponse.builder()
                .biblioteca(biblioteca)
                .livroList(livroList)
                .cache(isCache)
                .build();
    }

}
