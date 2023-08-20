package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.service.BookstoreDatabaseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CustomerController {
    private final BookstoreDatabaseService bookstoreDatabaseService;

    public CustomerController(BookstoreDatabaseService bookstoreDatabaseService) {
        this.bookstoreDatabaseService = bookstoreDatabaseService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Book> getBooksByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                     @RequestParam(name = "size", defaultValue = "50") int size) {
        return this.bookstoreDatabaseService.getBooksByPage(page, size);
    }
}
