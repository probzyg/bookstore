package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.request.AddBookRequest;
import com.example.bookstore.service.BookstoreDatabaseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AdminController {
    private final BookstoreDatabaseService bookstoreDatabaseService;

    public AdminController(BookstoreDatabaseService bookstoreDatabaseService) {
        this.bookstoreDatabaseService = bookstoreDatabaseService;
    }

    @GetMapping("/add-book")
    public String showAddBookPage(Model model) {
        model.addAttribute("addBookRequest", new AddBookRequest());
        return "add-book";
    }

    @PostMapping("/add-book")
    @ResponseStatus(HttpStatus.CREATED)
    public String addBook(@ModelAttribute AddBookRequest addBookRequest, Model model) {
        try {
            Book newBook = bookstoreDatabaseService.addBook(addBookRequest);
            model.addAttribute("newBook", newBook);
            return "book-added";
        } catch (ResponseStatusException e) {
            model.addAttribute("bookTitle", addBookRequest.getTitle());
            return "book-conflict";
        }
    }
}
