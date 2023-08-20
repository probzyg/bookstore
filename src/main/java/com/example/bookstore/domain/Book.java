package com.example.bookstore.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    public Book() {
        this.createdAt = LocalDateTime.now();
    }
}
