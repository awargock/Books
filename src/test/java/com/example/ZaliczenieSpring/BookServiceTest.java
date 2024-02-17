package com.example.ZaliczenieSpring;

import com.example.ZaliczenieSpring.Model.Book;
import com.example.ZaliczenieSpring.Repository.BookDaoRepository;
import com.example.ZaliczenieSpring.Repository.BookRepository;
import com.example.ZaliczenieSpring.Service.BookService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.core.env.Environment;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository h2BookRepository;

    @Mock
    private BookDaoRepository bookDaoRepository;

    @Mock
    private Environment environment;

    @Test
    public void getAllBooks_DaoProfile() {

        when(environment.getActiveProfiles()).thenReturn(new String[]{"list"});
        Book book1 = new Book("1984", "George Orwell", "opis 1984");
        Book book2 = new Book("Kod Leonarda da Vinci", "Dan Brown", "opis kodu leonarda da Vinci");
        List<Book> expectedBooks = Arrays.asList(book1, book2);
        when(bookDaoRepository.findAll()).thenReturn(expectedBooks);

        List<Book> actualBooks = bookService.getAllBooks();

        assertEquals(expectedBooks, actualBooks);
    }

    @Test
    public void updateBook_DaoProfile() {

        when(environment.getActiveProfiles()).thenReturn(new String[]{"h2"});
        Book existingBook1 = new Book(1L, "1984", "George Orwell", "opis 1984");
        Book existingBook2 = new Book(2L, "Kod Leonarda da Vinci", "Dan Brown", "opis kodu leonarda da Vinci");
        Book updatedBook = new Book(1L, "1984", "George Orwell", "nowy opis");

        h2BookRepository.save(existingBook1);
        h2BookRepository.save(existingBook2);

        when(h2BookRepository.findById(1L)).thenReturn(Optional.of(existingBook1));

        Book result = bookService.updateBook(1L, updatedBook);

        assertEquals(updatedBook.getId(), result.getId());
        assertEquals(updatedBook.getTitle(), result.getTitle());
        assertEquals(updatedBook.getAuthor(), result.getAuthor());
        assertEquals(updatedBook.getDescription(), result.getDescription());
    }

    // Add similar tests for createBook, updateBook, and deleteBook methods

    @Test(expected = EntityNotFoundException.class)
    public void updateBook_BookNotFound_ThrowsEntityNotFoundException() {
        // Arrange
        when(environment.getActiveProfiles()).thenReturn(new String[]{"list"});
        Long bookId = 1L;
        Book updatedBook = new Book("UpdatedTitle", "UpdatedAuthor", "UpdatedDescription");
        when(bookDaoRepository.findById(bookId)).thenReturn(Optional.empty());

        // Act
        bookService.updateBook(bookId, updatedBook);

    }

}

