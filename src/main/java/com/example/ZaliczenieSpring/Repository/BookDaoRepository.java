package com.example.ZaliczenieSpring.Repository;

import com.example.ZaliczenieSpring.Model.Book;

import java.util.List;
import java.util.Optional;


public interface BookDaoRepository {
    List<Book> findAll();

    Optional<Book> findById(Long id);

    Book save(Book book);

    Book updateBook(Long id, Book updatedBook);

    void deleteById(Long id);
}

