package am.solido.restexample.dto;

import am.solido.restexample.model.Author;
import am.solido.restexample.model.BookLanguage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBookDto {

    private String title;
    private Author author;
    private double price;
    private BookLanguage language;
}
