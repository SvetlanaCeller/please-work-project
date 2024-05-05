package ru.itgirl.pleaseworkproject.test.controllerTest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.itgirl.pleaseworkproject.dto.AuthorDto;
import ru.itgirl.pleaseworkproject.dto.BookDto;
import ru.itgirl.pleaseworkproject.model.Book;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBookByNameV1() throws Exception {
        String name = "Нос";
        BookDto bookDto = new BookDto();
        bookDto.setId(1L);
        bookDto.setName(name);
        bookDto.setGenre("Рассказ");

        mockMvc.perform(MockMvcRequestBuilders.get("/book", name))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.surname").value(bookDto.getGenre()));
    }
}
