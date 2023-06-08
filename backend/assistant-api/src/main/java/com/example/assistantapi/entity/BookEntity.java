package com.example.assistantapi.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "book")
public class BookEntity {

    @Id
    private Long id;
    private String name;
    private String author;
    private String gender;
    private String theme;
    private String description;
    private String bookCase;

}
