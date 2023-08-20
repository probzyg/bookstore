package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.request.AddBookRequest;
import com.example.bookstore.service.BookstoreDatabaseService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminController {
    private final BookstoreDatabaseService bookstoreDatabaseService;

    public AdminController(BookstoreDatabaseService bookstoreDatabaseService) {
        this.bookstoreDatabaseService = bookstoreDatabaseService;
    }

    @PutMapping("/add-book")
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody AddBookRequest addBookRequest) {
        return bookstoreDatabaseService.addBook(addBookRequest);
    }
}
