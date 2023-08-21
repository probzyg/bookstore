package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.request.AddBookRequest;
import com.example.bookstore.request.UpdatePriceRequest;
import com.example.bookstore.service.BookstoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@Controller
public class AdminController {
    private final BookstoreService bookstoreService;

    public AdminController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @GetMapping("/add-book")
    public String showAddBookPage(Model model) {
        model.addAttribute("addBookRequest", new AddBookRequest());
        return "add-book";
    }

    @PostMapping("/add-book")
    @ResponseStatus(HttpStatus.CREATED)
    public String addBook(@ModelAttribute @Valid AddBookRequest addBookRequest,
                          BindingResult bindingResult,
                          Model model) {
        if (bindingResult.hasErrors()) {
            return "price-not-valid"; // Return to the add book form with validation errors
        }

        try {
            Book newBook = bookstoreService.addBook(addBookRequest);
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
    public String updatePriceToBook(@ModelAttribute @Valid UpdatePriceRequest updatePriceRequest,
                                    BindingResult bindingResult,
                                    Model model) {
        if (bindingResult.hasErrors()) {
            return "price-not-valid-update";
        }
        try {
            Book updatedBook = bookstoreService.updatePriceToBook(updatePriceRequest);
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
