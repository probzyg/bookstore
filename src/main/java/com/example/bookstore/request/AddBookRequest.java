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
        String trimmedTitle = title != null ? title.trim() : null;
        String trimmedAuthorName = authorName != null ? authorName.trim() : null;
        String trimmedGenre = genre != null ? genre.trim() : null;

        return !(trimmedTitle == null || trimmedTitle.isEmpty() ||
                trimmedAuthorName == null || trimmedAuthorName.isEmpty() ||
                trimmedGenre == null || trimmedGenre.isEmpty());
    }
}
