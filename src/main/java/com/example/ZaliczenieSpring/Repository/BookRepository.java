package com.example.ZaliczenieSpring.Repository;

import com.example.ZaliczenieSpring.Model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
