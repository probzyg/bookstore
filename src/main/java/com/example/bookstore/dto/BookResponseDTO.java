package com.example.bookstore.dto;

import com.example.bookstore.domain.Book;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BookResponseDTO {
    private List<Book> content;
    public BookResponseDTO(List<Book> content) {
        this.content = content;
    }
}
