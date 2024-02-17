package com.example.ZaliczenieSpring;

import com.example.ZaliczenieSpring.Model.Book;
import com.example.ZaliczenieSpring.Repository.BookDaoRepository;
import com.example.ZaliczenieSpring.Repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
public class BookIntegrationTests {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private BookRepository h2BookRepository;

    @Autowired
    private BookDaoRepository bookDaoRepository;

    @Autowired
    private DataInitializer dataInitializer;

    @LocalServerPort
    private int port;

    private String getBaseUrl() {
        return "http://localhost:" + port;
    }

    @Test
    public void getAllBooks_ListProfileActive_ReturnsBooksFromDaoRepository() {

        h2BookRepository.deleteAll(); // Clear H2 database for consistency
        bookDaoRepository.save(new Book("Title1", "Author1", "Description1"));
        bookDaoRepository.save(new Book("Title2", "Author2", "Description2"));

        ResponseEntity<List<Book>> response = restTemplate.exchange(
                getBaseUrl() + "/books",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Book>>() {
                });


        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Book> books = response.getBody();
        assertNotNull(books);
        assertEquals(2, books.size());
    }

    @Test
    public void getBookById_ListProfileActive_ReturnsBookFromDaoRepository() {
        // Arrange
        h2BookRepository.deleteAll(); // Clear H2 database for consistency
        Book savedBook = bookDaoRepository.save(new Book("Title1", "Author1", "Description1"));

        // Act
        ResponseEntity<Book> response = restTemplate.getForEntity(
                getBaseUrl() + "/book/" + savedBook.getId(),
                Book.class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Book retrievedBook = response.getBody();
        assertNotNull(retrievedBook);
        assertEquals(savedBook.getId(), retrievedBook.getId());
    }

    // Add more integration tests for other scenarios as needed
}

