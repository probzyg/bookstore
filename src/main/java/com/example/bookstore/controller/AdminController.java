package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.request.AddBookRequest;
import com.example.bookstore.request.UpdatePriceRequest;
import com.example.bookstore.service.BookstoreDatabaseService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;

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
            if (e.getStatusCode() == HttpStatus.CONFLICT) {
                model.addAttribute("bookTitle", addBookRequest.getTitle());
                return "book-conflict";
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                return "bad-request";
            }
            throw new RuntimeException("Something went terribly wrong!");
        }
    }

    @GetMapping("/update-price")
    public String showUpdatePricePage(Model model) {
        model.addAttribute("updatePriceRequest", new UpdatePriceRequest());
        return "update-price";
    }

    @PostMapping("/update-price")
    @ResponseStatus(HttpStatus.OK)
    public String addPriceToBook(@ModelAttribute UpdatePriceRequest updatePriceRequest, Model model) {
        try {
            Book updatedBook = bookstoreDatabaseService.addPriceToBook(updatePriceRequest);
            model.addAttribute("updatedBook", updatedBook);
            return "price-updated";
        } catch (ResponseStatusException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                return "book-conflict";
            }
            throw e;
        }
    }
}
