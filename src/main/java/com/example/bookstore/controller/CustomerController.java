package com.example.bookstore.controller;

import com.example.bookstore.domain.Book;
import com.example.bookstore.service.BookstoreService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CustomerController {
    private final BookstoreService bookstoreService;

    public CustomerController(BookstoreService bookstoreService) {
        this.bookstoreService = bookstoreService;
    }

    @GetMapping
    public String showBookList(@RequestParam(name = "page", defaultValue = "1") int page,
                               @RequestParam(name = "size", defaultValue = "50") int size,
                               Model model) {
        List<Book> books = bookstoreService.getBookPage(page, size).getContent();
        model.addAttribute("books", books);

        Page<Book> bookPage = bookstoreService.getBookPage(page, size);
        model.addAttribute("pageInfo", bookPage);

        return "books";
    }
}
