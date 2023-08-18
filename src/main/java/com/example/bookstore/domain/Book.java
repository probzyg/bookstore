package com.example.bookstore.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @Column(name = "title")
    private String title;
    @Column(name = "author_name")
    private String authorName;
    @Column(name = "genre")
    private String genre;

    public Book() {
    }
}
