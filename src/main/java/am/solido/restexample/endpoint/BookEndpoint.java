package am.solido.restexample.endpoint;

import am.solido.restexample.dto.BookResponseDto;
import am.solido.restexample.dto.BookUpdateDto;
import am.solido.restexample.dto.CreateBookDto;
import am.solido.restexample.mapper.BookMapper;
import am.solido.restexample.model.Book;
import am.solido.restexample.repository.BookRepository;
import am.solido.restexample.security.CurrentUser;
import am.solido.restexample.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Log4j2
public class BookEndpoint {

    private final BookService bookService;

    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    private final RestTemplate restTemplate;

//    Logger log = LoggerFactory.getLogger(AuthorEndpoint.class.getName());

    @Value("${cb.url}")
    private String cbUrl;


    @GetMapping("/books")
    public List<Book> getAllBooks(@AuthenticationPrincipal CurrentUser currentUser) {
        log.info("endpoint / books called by {}",currentUser.getUser().getEmail());
        List<Book> all = bookRepository.findAll();
        if(!all.isEmpty()) {
            ResponseEntity<HashMap> currency = restTemplate.getForEntity(cbUrl + "?currency=USD", HashMap.class);
            HashMap<String, String> currencyBody = currency.getBody();
            if (!currencyBody.isEmpty()) {
                double usdCurrency = Double.parseDouble(currencyBody.get("USD"));
                if (usdCurrency > 0) {
                    for (Book book : all) {
                        double price = book.getPrice() / usdCurrency;
                        DecimalFormat df = new DecimalFormat("#.##");
                        book.setPrice(Double.valueOf(df.format(price)));
                    }
                }
            }
        }
        return all;
    }

    @GetMapping("/books/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable("id") int id) {
        Optional<Book> byId = bookRepository.findById(id);
        return byId.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/books")
    public ResponseEntity<Book> addBook(@RequestBody CreateBookDto createBookDto) {
        return ResponseEntity.ok(bookService.saveBook(bookMapper.map(createBookDto)));
    }

    @PutMapping("/books/{id}")
    public ResponseEntity<?> editBookById(@PathVariable("id") int id,
                                          @RequestBody BookUpdateDto bookUpdateDto) {
        Book book = bookMapper.map(bookUpdateDto);
        if(book == null){
            return ResponseEntity.badRequest().build();
        }
        book.setId(id);
        return ResponseEntity.ok(bookRepository.save(book));
    }

    @DeleteMapping("/books/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") int id) {
        bookRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

