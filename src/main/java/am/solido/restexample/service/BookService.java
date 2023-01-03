package am.solido.restexample.service;

import am.solido.restexample.model.Book;
import am.solido.restexample.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    public Book saveBook(Book book){
        if(book == null) {
            throw new RuntimeException("Book can't be null");
        }
        return bookRepository.save(book);
    }

}
