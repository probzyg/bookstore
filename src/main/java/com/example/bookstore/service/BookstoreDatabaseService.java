package com.example.bookstore.service;

import com.example.bookstore.domain.Book;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.repository.BookDatabaseRepository;
import com.example.bookstore.request.AddBookRequest;
import com.example.bookstore.request.UpdatePriceRequest;
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
        if (validateRequest(addBookRequest)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The values can't be blank!");
        }
        this.bookDatabaseRepository.save(newBook);
        return newBook;
    }

    private boolean validateRequest(AddBookRequest addBookRequest) {
        String title = addBookRequest.getTitle().trim();
        String authorName = addBookRequest.getAuthorName().trim();
        String genre = addBookRequest.getGenre().trim();
        return title.equals("") || authorName.equals("") || genre.equals("");
    }

    private Book createBook(AddBookRequest addBookRequest) {
        Book newBook = new Book();

        String price = addBookRequest.getPrice().trim();
        if (!price.equals("")) {
            newBook.setPrice(new BigDecimal(price));
        }

        newBook.setTitle(addBookRequest.getTitle());
        newBook.setAuthorName(addBookRequest.getAuthorName());
        newBook.setGenre(addBookRequest.getGenre());
        return newBook;
    }

    @Transactional(readOnly = true)
    public Page<Book> getBookPage(int pageNumber, int pageSize) {
        Pageable pageable = createPageable(pageNumber, pageSize);
        return this.bookDatabaseRepository.findAllByOrderByCreatedAtDesc(pageable);
    }

    @Transactional(readOnly = true)
    public List<BookDTO> getAllBookWithPrices(int pageNumber, int pageSize) {
        Pageable pageable = createPageable(pageNumber, pageSize);
        Page<BookDTO> page = this.bookDatabaseRepository.findAllBookWithPrices(pageable);
        return page.getContent();
    }

    private Pageable createPageable(int pageNumber, int pageSize) {
        int adjustedPage = pageNumber - 1;
        return PageRequest.of(adjustedPage, pageSize);
    }

    @Transactional
    public Book addPriceToBook(UpdatePriceRequest updatePriceRequest) {
        Book existingBook = bookDatabaseRepository.findBookByTitle(updatePriceRequest.getTitle());

        if (existingBook != null) {
            existingBook.setPrice(new BigDecimal(updatePriceRequest.getPrice()));
            bookDatabaseRepository.save(existingBook);
            return existingBook;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The book was not found in our database");
        }
    }
}
