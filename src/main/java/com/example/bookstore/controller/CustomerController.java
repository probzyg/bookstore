package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.service.BookstoreDatabaseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping
public class CustomerController {
    private final BookstoreDatabaseService bookstoreDatabaseService;

    public CustomerController(BookstoreDatabaseService bookstoreDatabaseService) {
        this.bookstoreDatabaseService = bookstoreDatabaseService;
    }

    @GetMapping("/main")
    @ResponseStatus(HttpStatus.OK)
    public List<Book> fetchAllBooks() {
        return this.bookstoreDatabaseService.fetchAllBooks();
    }
}
