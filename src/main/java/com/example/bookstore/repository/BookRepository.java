package com.example.bookstore.repository;

import com.example.bookstore.domain.Book;
import com.example.bookstore.dto.BookDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, String> {
    Book findBookByTitle(String title);

    Page<Book> findAllByOrderByCreatedAtDesc(Pageable pageable);

    @Query("SELECT new com.example.bookstore.dto.BookDTO(b.title, b.price) FROM Book b WHERE b.price IS NOT NULL ORDER BY b.createdAt DESC")
    List<BookDTO> findAllBookWithPrices();
}
