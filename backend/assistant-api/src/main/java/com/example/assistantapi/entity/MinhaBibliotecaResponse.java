package com.example.assistantapi.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MinhaBibliotecaResponse {

    private String titulo;
    private String autor;
    private String biblioteca;
    private String urlAcesso;

}
