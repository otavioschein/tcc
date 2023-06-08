package com.example.assistantapi.repository;

import com.example.assistantapi.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    @Query(value = "select name from book", nativeQuery = true)
    List<String> findAllBooksNames();

    List<BookEntity> findAllByAuthor(String author);

}
