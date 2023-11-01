package com.example.assistantapi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssistantResponse {

    private String biblioteca;
    private List<LivroResponse> livroList;
    private boolean cache;

}
