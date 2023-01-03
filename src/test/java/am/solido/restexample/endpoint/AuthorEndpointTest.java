package am.solido.restexample.endpoint;

import am.solido.restexample.model.Author;
import am.solido.restexample.model.Gender;
import am.solido.restexample.repository.AuthorRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;


import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc

class AuthorEndpointTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AuthorRepository authorRepository;

    private final static String BASE_URL = "http://localhost:8080";

    @BeforeEach
    public void beforeEach(){
        authorRepository.deleteAll();
    }
    @Test
    public void testGetAllAuthorsEndpoint() throws Exception {
        addTestAuthors();
       mockMvc.perform(get(BASE_URL + "/author")
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$",hasSize(2)));
    }
    @Test
    public void addTestAuthor() throws Exception {
        ObjectNode objectNode = new ObjectMapper().createObjectNode();
        objectNode.put("name","poxos");
        objectNode.put("surname","poxosyan");
        objectNode.put("email","poxos111@mail.ru");
        objectNode.put("gender","MALE");
        mockMvc.perform(post(BASE_URL + "/author")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectNode.toString()))
                .andExpect(status().isOk());
    }

    private void addTestAuthors() {
        authorRepository.save(Author.builder()
                        .name("poxos")
                        .surname("poxosyan")
                        .email("poxos@mail.ru")
                        .gender(Gender.MALE)
                .build());
        authorRepository.save(Author.builder()
                .name("petros")
                .surname("petrosyan")
                .email("petros@mail.ru")
                .gender(Gender.MALE)
                .build());
    }


}