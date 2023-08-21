package com.example.bookstore.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddBookRequest {
    private String title;
    private String authorName;
    private String genre;
    private String price;

    public AddBookRequest() {
    }

    public boolean isValid() {
        return !title.isBlank() && !authorName.isBlank() && !genre.isBlank();
    }
}
