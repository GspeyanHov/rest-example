package am.solido.restexample.service;

import am.solido.restexample.model.Author;
import am.solido.restexample.model.Book;
import am.solido.restexample.model.BookLanguage;
import am.solido.restexample.repository.BookRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static am.solido.restexample.model.BookLanguage.ARMENIAN;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class BookServiceTest {

    @MockBean
    private BookRepository bookRepository;

    @Autowired
    private BookService bookService;
    @Test
    void saveBook() {
        Book book = Book.builder()
                .id(1)
                .price(15)
                .title("fgfg")
                .author(new Author())
                .build();
        when(bookRepository.save(any())).thenReturn(book);

        assertThrows(RuntimeException.class, () ->bookService.saveBook(null));

        bookService.saveBook(Book.builder()
                .price(15)
                .title("fgfg")
                .author(new Author())
                .build());
        verify(bookRepository,times(1)).save(any());
    }
}