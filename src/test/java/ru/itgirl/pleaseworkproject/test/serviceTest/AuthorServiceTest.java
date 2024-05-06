package ru.itgirl.pleaseworkproject.test.serviceTest;

import freemarker.template.utility.NullArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.jpa.domain.Specification;
import ru.itgirl.pleaseworkproject.dto.AuthorCreateDto;
import ru.itgirl.pleaseworkproject.dto.AuthorDto;
import ru.itgirl.pleaseworkproject.model.Author;
import ru.itgirl.pleaseworkproject.model.Book;
import ru.itgirl.pleaseworkproject.repository.AuthorRepository;
import ru.itgirl.pleaseworkproject.service.AuthorServiceImpl;

import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;

    @InjectMocks
    private AuthorServiceImpl authorService;

    @Test
    public void testGetAuthorById() {
        Long id = 1L;
        String name = "Александр";
        String surname = "Пушкин";
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);

        Mockito.when(authorRepository.findById(id)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorById(id);

        verify(authorRepository).findById(id);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByIdNotFound() {
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getAuthorById(id));

        verify(authorRepository).findById(id);
    }

    @Test
    public void testGetAuthorByNameV1() {
        String name = "Александр";
        String surname = "Пушкин";
        Long id = 1L;
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorByNameV1(name);

        verify(authorRepository).findAuthorByName(name);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());

    }

    @Test
    public void testGetAuthorByNameV1NotFound() {
        String name = "Иннокентий";

        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getAuthorByNameV1(name));

        verify(authorRepository).findAuthorByName(name);
    }

    @Test
    public void getAuthorByNameV2() {
        String name = "Николай";
        String surname = "Гоголь";
        Long id = 2L;
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);

        when(authorRepository.findAuthorByNameBySql(name)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorByNameV2(name);

        verify(authorRepository).findAuthorByNameBySql(name);

        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());

    }

    @Test
    public void testGetAuthorByNameV2NotFound() {
        String name = "Александр";

        when(authorRepository.findAuthorByNameBySql(name)).thenReturn(Optional.empty());

        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getAuthorByNameV2(name));

        verify(authorRepository).findAuthorByNameBySql(name);
    }

    @Test
    public void testGetAuthorByNameV3() {
        String name = "Александр";
        String surname = "Пушкин";
        Long id = 1L;
        Set<Book> books = new HashSet<>();
        Author author = new Author(id, name, surname, books);

        Specification<Author> specification = Specification.where((Specification<Author>)
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name));


        when(authorRepository.findOne(specification)).thenReturn(Optional.of(author));

        AuthorDto authorDto = authorService.getAuthorByNameV3(name);

        verify(authorRepository).findOne(specification);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testCreateAuthor_SavedAuthor(){
        AuthorCreateDto authorCreateDto = new AuthorCreateDto("Антон", "Чехов");
        authorService.createAuthor(authorCreateDto);
        //но в логах "Author is saved"
    }

    @Test
    public void testCreateAuthor_NullArgumentException(){
        AuthorCreateDto authorCreateDto = new AuthorCreateDto(" ", "Чехов");
        Assertions.assertThrows(NullArgumentException.class, () -> authorService.createAuthor(authorCreateDto));
    }
}