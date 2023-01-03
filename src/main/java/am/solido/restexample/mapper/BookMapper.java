package am.solido.restexample.mapper;

import am.solido.restexample.dto.*;
import am.solido.restexample.model.Author;
import am.solido.restexample.model.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book map(CreateBookDto createBookDto);

    AuthorResponseDto map(Author author);

    List<BookResponseDto> map(List<Book> booksList);

    Book map(BookUpdateDto BookUpdateDto);
}
