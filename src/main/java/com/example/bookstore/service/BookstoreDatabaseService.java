package com.example.bookstore.service;

import com.example.bookstore.domain.Book;
import com.example.bookstore.dto.BookResponseDTO;
import com.example.bookstore.repository.BookDatabaseRepository;
import com.example.bookstore.request.AddBookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class BookstoreDatabaseService {
    private final BookDatabaseRepository bookDatabaseRepository;

    public BookstoreDatabaseService(BookDatabaseRepository bookDatabaseRepository) {
        this.bookDatabaseRepository = bookDatabaseRepository;
    }

    @Transactional
    public synchronized Book addBook(AddBookRequest addBookRequest) {
        Book newBook = createBook(addBookRequest);
        if (this.bookDatabaseRepository.findBookByTitle(newBook.getTitle()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        this.bookDatabaseRepository.save(newBook);
        return newBook;
    }

    private Book createBook(AddBookRequest addBookRequest) {
        Book newBook = new Book();
        newBook.setTitle(addBookRequest.getTitle());
        newBook.setAuthorName(addBookRequest.getAuthorName());
        newBook.setGenre(addBookRequest.getGenre());
        return newBook;
    }

    @Transactional(readOnly = true)
    public BookResponseDTO getBooksByPage(int pageNumber, int pageSize) {
        int adjustedPage = pageNumber - 1;
        Pageable pageable = PageRequest.of(adjustedPage, pageSize);
        Page<Book> page = this.bookDatabaseRepository.findAllByOrderByCreatedAtDesc(pageable);
        return new BookResponseDTO(page.getContent());
    }
}
