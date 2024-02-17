package com.example.ZaliczenieSpring.Controller;

import com.example.ZaliczenieSpring.Model.Book;
import com.example.ZaliczenieSpring.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.core.env.Environment;

import java.util.List;
import java.util.Optional;
import org.springframework.security.core.Authentication;


@RestController
public class BookController {

    private final Environment environment;
    @Autowired
    private BookService bookService;

    public BookController(Environment environment) {
        this.environment = environment;
    }

    @GetMapping("/api/property")
    public ResponseEntity<String> getPropertyByName(@RequestParam String name) {
        //localhost:8080/api/property?name=spring.datasource.url
        String propertyValue = environment.getProperty(name);

        if (propertyValue == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(propertyValue);
    }

    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = bookService.getBookById(id);
        return book.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/book")
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createdBook = bookService.createBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
    }

    @PatchMapping("/book/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book updatedBook) {
        Book updated = bookService.updateBook(id, updatedBook);
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id, Authentication authentication) {
        bookService.deleteBook(id);
        return ResponseEntity.noContent().build();
    }
}

