package com.example.bookstore.service;

import com.example.bookstore.domain.Book;
import com.example.bookstore.dto.BookDTO;
import com.example.bookstore.repository.BookRepository;
import com.example.bookstore.request.AddBookRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class BookstoreServiceTest {

    @Mock
    BookRepository bookRepository;

    @InjectMocks
    BookstoreService bookstoreService;


    @Test
    @DisplayName("Should be able to add book without a price")
    public void shouldBeAbleToAddBookWithoutPrice() {
        AddBookRequest addBookRequest = new AddBookRequest();
        addBookRequest.setTitle("Book Title");
        addBookRequest.setAuthorName("Author Name");
        addBookRequest.setGenre("Fiction");
        addBookRequest.setPrice(BigDecimal.valueOf(10.99));

        Book addedBook = bookstoreService.addBook(addBookRequest);


        assertNotNull(addedBook);
        assertEquals("Book Title", addedBook.getTitle());
        assertEquals("Author Name", addedBook.getAuthorName());
        assertEquals("Fiction", addedBook.getGenre());
        assertEquals(new BigDecimal("10.99"), addedBook.getPrice());
    }

    @Test
    @DisplayName("Should get paginated book list")
    public void shouldGetPaginatedBookList() {
        List<Book> sampleBooks = new ArrayList<>();
        sampleBooks.add(new Book("Book 1", "Author 1", "Fiction", BigDecimal.valueOf(10.99)));
        sampleBooks.add(new Book("Book 2", "Author 2", "Fantasy", BigDecimal.valueOf(15.99)));

        Page<Book> samplePage = new PageImpl<>(sampleBooks);

        when(bookRepository.findAllByOrderByCreatedAtDesc(any(Pageable.class)))
                .thenReturn(samplePage);

        Page<Book> resultPage = bookstoreService.getBookPage(1, 10);

        assertNotNull(resultPage);
        assertEquals(2, resultPage.getTotalElements());
        assertEquals("Book 1", resultPage.getContent().get(0).getTitle());
        assertEquals("Book 2", resultPage.getContent().get(1).getTitle());

        verify(bookRepository).findAllByOrderByCreatedAtDesc(PageRequest.of(0, 10));
    }

    @Test
    @DisplayName("Should get list of books with prices")
    public void shouldGetListOfBooksWithPrices() {
        List<BookDTO> sampleBookDTOs = new ArrayList<>();
        sampleBookDTOs.add(new BookDTO("Book 1", BigDecimal.valueOf(10.99)));
        sampleBookDTOs.add(new BookDTO("Book 2", BigDecimal.valueOf(15.99)));

        when(bookRepository.findAllBookWithPrices())
                .thenReturn(sampleBookDTOs);

        List<BookDTO> resultBookDTOs = bookstoreService.getAllBookWithPrices();

        assertNotNull(resultBookDTOs);
        assertEquals(2, resultBookDTOs.size());
        assertEquals("Book 1", resultBookDTOs.get(0).getTitle());
        assertEquals(BigDecimal.valueOf(10.99), resultBookDTOs.get(0).getPrice());
        assertEquals("Book 2", resultBookDTOs.get(1).getTitle());
        assertEquals(BigDecimal.valueOf(15.99), resultBookDTOs.get(1).getPrice());

        verify(bookRepository).findAllBookWithPrices();
    }
}