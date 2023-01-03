package am.solido.restexample.endpoint;

import am.solido.restexample.dto.AuthorResponseDto;
import am.solido.restexample.dto.CreateAuthorDto;
import am.solido.restexample.mapper.AuthorMapper;
import am.solido.restexample.model.Author;
import am.solido.restexample.repository.AuthorRepository;
import am.solido.restexample.security.CurrentUser;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthorEndpoint {

   private final AuthorRepository authorRepository;
   private final AuthorMapper authorMapper;

   @Operation(operationId = "get all authors",
                  summary = "method allows to get all authors list",
                   description = "this request is open for every client"
               )

    @GetMapping("/author")
    public List<AuthorResponseDto> getAuthors(@AuthenticationPrincipal CurrentUser currentUser) {
//        log.info("endpoint / authors called by {}",currentUser.getUser().getEmail());
        return authorMapper.map(authorRepository.findAll());
    }

    @GetMapping("/author/{id}")
        public ResponseEntity<Author> getAuthorById(@PathVariable("id") int id){
        Optional<Author> byId = authorRepository.findById(id);
        if(!byId.isPresent()){
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok().build();
    }
    @PostMapping("/author")
    public ResponseEntity<Author> addAuthor(@RequestBody CreateAuthorDto createAuthorDto){
        Author author = authorRepository.save(authorMapper.map(createAuthorDto));
        return ResponseEntity.ok(author);
    }

}
