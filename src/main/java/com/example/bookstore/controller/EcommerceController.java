package com.example.bookstore.controller;

import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.service.BookstoreDatabaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;

@Controller
@RequestMapping("/e-com")
public class EcommerceController {
    private final BookstoreDatabaseService bookstoreDatabaseService;

    public EcommerceController(BookstoreDatabaseService bookstoreDatabaseService) {
        this.bookstoreDatabaseService = bookstoreDatabaseService;
    }

    @GetMapping
    public String getBooksWithNameAndPrice(@RequestParam(name = "page", defaultValue = "1") int page,
                                           @RequestParam(name = "size", defaultValue = "50") int size,
                                           Model model) {
        List<BookDTO> bookDTOs = this.bookstoreDatabaseService.getAllBookWithPrices(page, size);
        model.addAttribute("books", bookDTOs);
        return "ecom-books";
    }
}