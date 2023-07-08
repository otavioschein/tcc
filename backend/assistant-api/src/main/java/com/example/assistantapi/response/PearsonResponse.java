package com.example.assistantapi.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PearsonResponse {

    private String id;
    private String autor;
    private String titulo;
    private String biblioteca;
    private String urlAcesso;

}
