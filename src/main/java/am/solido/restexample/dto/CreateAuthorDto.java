package am.solido.restexample.dto;

import am.solido.restexample.model.Gender;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateAuthorDto {

    private String name;
    private String surname;
    private String email;
    private Gender gender;
}
