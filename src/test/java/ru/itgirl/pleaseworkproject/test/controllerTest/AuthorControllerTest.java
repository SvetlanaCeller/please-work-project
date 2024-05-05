package ru.itgirl.pleaseworkproject.test.controllerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.itgirl.pleaseworkproject.dto.AuthorDto;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class AuthorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetAuthorById() throws Exception {
        Long authorId = 1L;
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(authorId);
        authorDto.setName("Александр");
        authorDto.setSurname("Пушкин");

        mockMvc.perform(MockMvcRequestBuilders.get("/author/{id}", authorId))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }



    @Test
    public void testGetAuthorByNameV1() throws Exception {
        String name = "Александр";
        AuthorDto authorDto = new AuthorDto();
        authorDto.setId(1L);
        authorDto.setName(name);
        authorDto.setSurname("Пушкин");

        mockMvc.perform(MockMvcRequestBuilders.get("/author", name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(authorDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(authorDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(authorDto.getSurname()));
    }
}