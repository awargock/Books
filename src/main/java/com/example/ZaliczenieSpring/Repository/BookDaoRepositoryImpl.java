package com.example.ZaliczenieSpring.Repository;

import com.example.ZaliczenieSpring.Model.Book;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BookDaoRepositoryImpl implements BookDaoRepository {
    private final List<Book> books = new ArrayList<>();
    private long nextId = 1;

    @Override
    public List<Book> findAll() {
        return new ArrayList<>(books);
    }

    @Override
    public Optional<Book> findById(Long id) {
        return books.stream().filter(book -> book.getId().equals(id)).findFirst();
    }

    @Override
    public Book save(Book book) {
        if (book.getId() == null) {
            book.setId(nextId++);
        } else {
            books.removeIf(b -> b.getId().equals(book.getId()));
        }
        books.add(book);
        return book;
    }

    @Override
    public Book updateBook(Long id, Book updatedBook) {
        Optional<Book> optionalBook = findById(id);
        if (optionalBook.isPresent()) {
            Book existingBook = optionalBook.get();
            existingBook.setTitle(updatedBook.getTitle());
            existingBook.setAuthor(updatedBook.getAuthor());
            existingBook.setDescription(updatedBook.getDescription());
            return existingBook;
        } else {
            throw new EntityNotFoundException("Book not found with ID: " + id);
        }
    }

    @Override
    public void deleteById(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }
}

