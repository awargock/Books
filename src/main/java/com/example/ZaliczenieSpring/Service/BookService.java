package com.example.ZaliczenieSpring.Service;

import com.example.ZaliczenieSpring.Model.Book;
import com.example.ZaliczenieSpring.Repository.BookDaoRepository;
import com.example.ZaliczenieSpring.Repository.BookRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository h2BookRepository;

    @Autowired
    private BookDaoRepository bookDaoRepository;

    @Autowired
    private Environment environment;

    public List<Book> getAllBooks() {
        if (isListProfileActive()) {
            return bookDaoRepository.findAll();
        } else {
            return h2BookRepository.findAll();
        }
    }

    public Optional<Book> getBookById(Long id) {
        if (isListProfileActive()) {
            return bookDaoRepository.findById(id);
        } else {
            return h2BookRepository.findById(id);
        }
    }

    public Book createBook(Book book) {
        if (isListProfileActive()) {
            return bookDaoRepository.save(book);
        } else {
            return h2BookRepository.save(book);
        }
    }

    public Book updateBook(Long id, Book updatedBook) {
        if (isListProfileActive()) {
            return bookDaoRepository.updateBook(id, updatedBook);
        } else {
            Optional<Book> optionalBook = h2BookRepository.findById(id);
            if (optionalBook.isPresent()) {
                Book existingBook = optionalBook.get();
                existingBook.setTitle(updatedBook.getTitle());
                existingBook.setAuthor(updatedBook.getAuthor());
                existingBook.setDescription(updatedBook.getDescription());
                return h2BookRepository.save(existingBook);
            } else {
                throw new EntityNotFoundException("Book not found with ID: " + id);
            }
        }
    }

    public void deleteBook(Long id) {
        if (isListProfileActive()) {
            bookDaoRepository.deleteById(id);
        } else {
            h2BookRepository.deleteById(id);
        }
    }

    private boolean isListProfileActive() {
        return Arrays.asList(environment.getActiveProfiles()).contains("list");
    }
}

