package ru.itgirl.pleaseworkproject.service;

import freemarker.template.utility.NullArgumentException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.pleaseworkproject.dto.*;
import ru.itgirl.pleaseworkproject.model.Author;
import ru.itgirl.pleaseworkproject.repository.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDto getAuthorById(Long id) {
        log.info("Try to find author by id {}", id);
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDto getByNameV1(String name) {
        log.info("Try to find author by name {}", name);
        Optional<Author> author = authorRepository.findAuthorByName(name);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("There is no such element");
        }
    }


    @Override
    public AuthorDto getByNameV2(String name) {
        log.info("Try to find author by name {}", name);
        Optional<Author> author = authorRepository.findBookByNameBySql(name);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("There is no such element");
        }
    }

    @Override
    public AuthorDto getByNameV3(String name) {
        log.info("Try to find author by name {}", name);
        Specification<Author> specification = Specification.where((Specification<Author>)
                (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("name"), name));

        Optional<Author> author = authorRepository.findOne(specification);
        if (author.isPresent()) {
            AuthorDto authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("There is no such element");
        }
    }

    @Override
    public AuthorDto createAuthor(AuthorCreateDto authorCreateDto) {
        log.info("Try to create the author");
        if (!authorCreateDto.getName().isBlank()) {
            Author author = authorRepository.save(convertDtoToEntity(authorCreateDto));
            log.info("Author is saved");
            return Optional.ofNullable(convertEntityToDto(author)).get();
        } else {
            log.error("Author's name is required.");
            throw new NullArgumentException("You didn't specify an author's name.");
        }
    }

    private Author convertDtoToEntity(AuthorCreateDto authorCreateDto) {
        return Author.builder()
                .name(authorCreateDto.getName())
                .surname(authorCreateDto.getSurname())
                .build();
    }

    @Override
    public List<AuthorDtoWithoutBooks> getAllAuthors() {
        log.info("Trying to find all the authors.");
        List<Author> authors = authorRepository.findAll();
        if (!authors.isEmpty()) {
            log.info("The authors are found.");
            return authors.stream().map(this::convertEntityToDtoWithoutBooks).collect(Collectors.toList());
        } else {
            log.error("Authors not found");
            throw new NoSuchElementException("There are no such elements");
        }
    }

    private AuthorDto convertEntityToDto(Author author) {
        List<BookDto> bookDtoList = null;
        if (author.getBooks() != null) {
            bookDtoList = author.getBooks()
                    .stream()
                    .map(book -> BookDto.builder()
                            .genre(book.getGenre().getName())
                            .name(book.getName())
                            .id(book.getId())
                            .build())
                    .toList();
        }
        return AuthorDto.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .books(bookDtoList)
                .build();
    }

    private AuthorDtoWithoutBooks convertEntityToDtoWithoutBooks(Author author) {
        return AuthorDtoWithoutBooks.builder()
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }

    @Override
    public AuthorDto updateAuthor(AuthorUpdateDto authorUpdateDto) {
        log.info("Trying to find the author by id for updating");
        Author author = authorRepository.findById(authorUpdateDto.getId()).orElseThrow();
        if (Optional.ofNullable(author.getId()).isPresent()) {
            log.info("The author is found");
            if (authorUpdateDto.getName().length() < 3 || authorUpdateDto.getName().length() > 10) {
                log.error("The length of the name is not correct.");
            } else {
                author.setName(authorUpdateDto.getName());
                log.info("The name is changed to {}", author.getName());
            }
            if (authorUpdateDto.getSurname().isEmpty()) {
                log.error("The surname can't be empty");
            } else {
                author.setSurname(authorUpdateDto.getSurname());
                log.info("The surname is changed to {}", author.getSurname());
            }
            Author savedAuthor = authorRepository.save(author);
            return convertEntityToDto(savedAuthor);
        } else {
            log.error("Author with id {} not found", authorUpdateDto.getId());
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Trying to delete the author.");
        ;
        authorRepository.deleteById(id);//как я понимаю, там и без меня уже прописали нужные логи
    }
}
