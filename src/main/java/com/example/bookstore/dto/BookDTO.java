package com.example.bookstore.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class BookDTO {
    private String title;
    private BigDecimal price;

    public BookDTO(String title, BigDecimal price) {
        this.title = title;
        this.price = price;
    }
}