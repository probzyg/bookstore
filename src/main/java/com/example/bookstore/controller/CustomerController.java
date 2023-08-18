package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.service.BookstoreDatabaseService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

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
    public Page<Book> getBooksByPage(@RequestParam(name = "page", defaultValue = "1") int page,
                                     @RequestParam(name = "size", defaultValue = "50") int size) {
        return this.bookstoreDatabaseService.getBooksByPage(page, size);
    }
}
