package com.example.ZaliczenieSpring;

import com.example.ZaliczenieSpring.Model.Book;
import com.example.ZaliczenieSpring.Repository.BookDaoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BookDaoRepository bookDaoRepository;

    public DataInitializer(BookDaoRepository bookDaoRepository) {
        this.bookDaoRepository = bookDaoRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        List<Book> bookList = prepareBookList();
        for (Book b : bookList) {
            bookDaoRepository.save(b);
        }
    }

    public List<Book> prepareBookList(){
        Book book1 = new Book("Chłopi", "Władysław Stanisław Reymont", "Powieść “Chłopi” ukazuje chłopską codzienność - ciężką pracę, walkę o kawałek chleba, swary, zazdrości, pożądania");
        Book book2 = new Book("1984", "George Orwell", "");
        Book book3 = new Book("Kod Leonarda da Vinci", "Dan Brown", "i");
        Book book4 = new Book("Cień wiatru", "Carlos Ruiz Zafón", "");
        Book book5 = new Book("Alchemik", "Paulo Coelho", "");

        return Arrays.asList(book1, book2, book3, book4, book5);
    }
}

