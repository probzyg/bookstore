package com.example.bookstore.repository;

import com.example.bookstore.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDatabaseRepository extends JpaRepository<Book, String> {
    Book findBookByTitle(String title);

    Page<Book> findAllByOrderByCreatedAtDesc(Pageable pageable);
}
