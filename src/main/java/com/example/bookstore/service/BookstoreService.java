package com.example.bookstore.service;

import com.example.bookstore.domain.Book;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.repository.BookRepository;
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
public class BookstoreService {
    private final BookRepository bookRepository;

    public BookstoreService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Transactional
    public synchronized Book addBook(AddBookRequest addBookRequest) {
        Book newBook = createBook(addBookRequest);
        if (this.bookRepository.findBookByTitle(newBook.getTitle()) != null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This book already exists");
        }
        if (!addBookRequest.isValid()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The values can't be blank!");
        }
        this.bookRepository.save(newBook);
        return newBook;
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


    public Page<Book> getBookPage(int pageNumber, int pageSize) {
        int adjustedPage = pageNumber - 1;
        Pageable pageable = PageRequest.of(adjustedPage, pageSize);
        return this.bookRepository.findAllByOrderByCreatedAtDesc(pageable);
    }


    public List<BookDTO> getAllBookWithPrices() {
        return this.bookRepository.findAllBookWithPrices();
    }

    
    public Book addPriceToBook(UpdatePriceRequest updatePriceRequest) {
        Book existingBook = bookRepository.findBookByTitle(updatePriceRequest.getTitle());

        if (existingBook != null) {
            existingBook.setPrice(new BigDecimal(updatePriceRequest.getPrice()));
            bookRepository.save(existingBook);
            return existingBook;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "The book was not found in our database");
        }
    }
}
