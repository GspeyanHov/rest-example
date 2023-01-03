package am.solido.restexample.mapper;

import am.solido.restexample.dto.AuthorResponseDto;
import am.solido.restexample.dto.CreateAuthorDto;
import am.solido.restexample.model.Author;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AuthorMapper {

    Author map(CreateAuthorDto createAuthorDto);

    AuthorResponseDto map(Author author);

    List<AuthorResponseDto> map(List<Author> authorList);
}
