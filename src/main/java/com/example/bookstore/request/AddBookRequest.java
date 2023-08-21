package com.example.bookstore.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AddBookRequest {
    private String title;
    private String authorName;
    private String genre;
    private BigDecimal price;

    public AddBookRequest() {
    }

    public boolean isValid() {
        return !title.isBlank() && !authorName.isBlank() && !genre.isBlank();
    }
}
