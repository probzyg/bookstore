package com.example.bookstore.service;

import com.example.bookstore.domain.Book;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.repository.BookDatabaseRepository;
import com.example.bookstore.request.AddBookRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.List;

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
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This book already exists");
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
    public List<Book> getBooksByPage(int pageNumber, int pageSize) {
        int adjustedPage = pageNumber - 1;
        Pageable pageable = PageRequest.of(adjustedPage, pageSize);
        Page<Book> page = this.bookDatabaseRepository.findAllByOrderByCreatedAtDesc(pageable);
        return page.getContent();
    }

    @Transactional(readOnly = true)
    public Page<Book> getBookPage(int pageNumber, int pageSize) {
        int adjustedPage = pageNumber - 1;
        Pageable pageable = PageRequest.of(adjustedPage, pageSize);
        return this.bookDatabaseRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Transactional(readOnly = true)
    public List<BookDTO> getAllBookWithPrices(int pageNumber, int pageSize) {
        int adjustedPage = pageNumber - 1;
        Pageable pageable = PageRequest.of(adjustedPage, pageSize);
        Page<BookDTO> page = this.bookDatabaseRepository.findAllBookWithPrices(pageable);
        return page.getContent();
    }

    @Transactional
    public Book addPriceToBook(String bookTitle, BigDecimal price) {
        Book existingBook = bookDatabaseRepository.findBookByTitle(bookTitle);

        if (existingBook != null) {
            existingBook.setPrice(price);
            bookDatabaseRepository.save(existingBook);
            return existingBook;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The book was not found in our database");
        }
    }
}
